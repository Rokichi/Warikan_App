package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.*
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.utils.getCalendarStr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

private val MAX_SUM_OF_PROPORTION = 24

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases,
    private val warikanUseCases: WarikanUseCases,
    private val settingsUseCases: SettingsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _rouletteState = MutableStateFlow(Roulette())
    val rouletteState = _rouletteState.asStateFlow()

    // 割り勘結果
    private val _resultWarikanState = MutableStateFlow<List<ResultWarikan>>(
        mutableListOf()
    )
    val resultWarikanState = _resultWarikanState.asStateFlow()

    // 合計比率
    var sumOfProportion = 0

    private val _deg = mutableStateOf(0f)
    val deg = _deg

    private val _isRotatingState = mutableStateOf(false)
    val isRotatingState: State<Boolean> = _isRotatingState

    private val _isMuteState = mutableStateOf(false)
    val isMuteState: State<Boolean> = _isMuteState

    private val _isRotated = mutableStateOf(false)
    val isRotated: State<Boolean> = _isRotated

    private val _isShowBottom = mutableStateOf(false)
    val isShowBottom: State<Boolean> = _isShowBottom

    private val _isShowResultOrRatio = mutableStateOf(false)
    val isShowResultOrRatio: State<Boolean> = _isShowResultOrRatio

    private val _resultDeg = mutableStateOf(0.0f)
    val resultDeg: State<Float> = _resultDeg

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // load from navigation
        val total = savedStateHandle.get<String>("total") ?: 0
        val memberData = savedStateHandle.get<Array<Member>>("members") ?: arrayOf()
        val warikanData = savedStateHandle.get<Array<Warikan>>("warikans") ?: arrayOf()

        val isSave = savedStateHandle.get<Boolean>("isSave") ?: false
        _rouletteState.value = Roulette(
            total = total.toString().toInt(),
            members = memberData.toList(),
            warikans = warikanData.toList()
        )
        rouletteState.value.warikans.forEach { warikan ->
            sumOfProportion += warikan.proportion
        }
        _deg.value = 360f / sumOfProportion
        _resultWarikanState.value =
            ResultWarikanFactory.create(rouletteState.value.members)
        if (isSave)
            viewModelScope.launch {
                // save
                val id = rouletteUseCases.addRoulette(
                    RouletteEntityFactory.create(
                        total.toString().toInt()
                    )
                )
                memberUseCases.addMember(MemberEntityFactory.create(id, memberData.toList()))
                warikanUseCases.addWarikan(WarikanEntityFactory.create(id, warikanData.toList()))
                _eventFlow.emit(UiEvent.SaveEvent)
            }
        lateinit var settings: Settings
        val job = CoroutineScope(Dispatchers.IO).launch {
            settings = SettingsFactory.create(settingsUseCases.getSettings())
        }
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        _isMuteState.value = settings.isMuted
    }

    val resultPayments = mutableListOf<Int>()
    val resultProportions = mutableListOf<Double>()

    fun onEvent(event: RoulettesEvent) {
        when (event) {
            is RoulettesEvent.MuteClickEvent -> {
                _isMuteState.value = event.isMuted
                if (event.isMuted) viewModelScope.launch { _eventFlow.emit(UiEvent.MuteON) }
                else viewModelScope.launch { _eventFlow.emit(UiEvent.MuteOFF) }
            }
            is RoulettesEvent.StartClickEvent -> {
                if (_isRotated.value) return
                _isShowResultOrRatio.value = true
                _isRotated.value = true
                viewModelScope.launch { _eventFlow.emit(UiEvent.StartRoulette) }
                _isRotatingState.value = true
                val warikans = _rouletteState.value.warikans
                // 割り勘結果のindex取得
                val drawnIndex =
                    rouletteUseCases.getRouletteResultIndex(warikans, getCalendarStr())
                // 目的回転角度取得
                _resultDeg.value = rouletteUseCases.getResultDeg(warikans, drawnIndex)
                // 割り勘情報取得
                val ratios: List<Int> = warikans[drawnIndex].ratios.map { it.toInt() }
                resultPayments += rouletteUseCases.getWarikanResult(
                    _rouletteState.value.total,
                    ratios
                )
                resultProportions +=
                    ratios.map { getDoubleOneDecimalPlaces(it.toDouble() / ratios.sum()) }
            }

            is RoulettesEvent.StopClickEvent -> {
                _isRotatingState.value = false
                viewModelScope.launch { _eventFlow.emit(UiEvent.StopRoulette) }
            }
            is RoulettesEvent.EndRouletteEvent -> {
                _resultWarikanState.value =
                    _resultWarikanState.value.mapIndexed { index, resultWarikan ->
                        resultWarikan.copy(
                            proportion = resultProportions[index],
                            payment = resultPayments[index]
                        )
                    }
                _isShowBottom.value = true
                viewModelScope.launch { _eventFlow.emit(UiEvent.EndRoulette) }
            }
            is RoulettesEvent.EditRatioButtonClick -> {
                val warikans = _rouletteState.value.warikans.mapIndexed { index, warikan ->
                    if (event.index == index) warikan.copy(
                        proportion = if (event.flg) plusProportion(
                            sumOfProportion,
                            warikan.proportion
                        ) else minusProportion(warikan.proportion)
                    )
                    else warikan
                }
                sumOfProportion = warikans.sumOf { it.proportion }
                _deg.value = 360f / sumOfProportion
                _rouletteState.value = _rouletteState.value.copy(warikans = warikans)
            }
            is RoulettesEvent.RetryClickEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.Retry) }
            }
            is RoulettesEvent.GoHomeClickEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.GoHome) }
            }
        }
    }

    private fun plusProportion(sum: Int, proportion: Int): Int {
        return if (sum + 1 <= MAX_SUM_OF_PROPORTION) proportion + 1
        else proportion
    }

    private fun minusProportion(proportion: Int): Int {
        return if (proportion - 1 > 0) proportion - 1
        else proportion
    }

    private fun getDoubleOneDecimalPlaces(num: Double): Double {
        return (num * 100).roundToInt() / 10.0
    }


    sealed class UiEvent {
        object SaveEvent : UiEvent()
        object StartRoulette : UiEvent()
        object StopRoulette : UiEvent()
        object MuteON : UiEvent()
        object MuteOFF : UiEvent()
        object EndRoulette : UiEvent()
        object Retry : UiEvent()
        object GoHome : UiEvent()
    }
}