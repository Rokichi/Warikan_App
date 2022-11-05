package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.ResultWarikanFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.utils.getCalendarStr
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt


@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases,
    private val warikanUseCases: WarikanUseCases,
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

    private val _isRotatingState = mutableStateOf(false)
    val isRotatingState: State<Boolean> = _isRotatingState

    private val _isMuteState = mutableStateOf(false)
    val isMuteState: State<Boolean> = _isMuteState

    private val _isRotated = mutableStateOf(false)
    val isRotated: State<Boolean> = _isRotated

    private val _isShowBottom = mutableStateOf(false)
    val isShowBottom: State<Boolean> = _isShowBottom

    private val _resultDeg = mutableStateOf(0.0f)
    val resultDeg: State<Float> = _resultDeg

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // load from navigation
        val total = savedStateHandle.get<String>("total") ?: 0
        val memberData = savedStateHandle.get<Array<Member>>("members") ?: arrayOf()
        val warikanData = savedStateHandle.get<Array<Warikan>>("warikans") ?: arrayOf()
        for (warikand in warikanData) Log.i("pro", warikand.proportion.toString())
        val isSave = savedStateHandle.get<Boolean>("isSave") ?: false
        _rouletteState.value = Roulette(
            Total = total.toString().toInt(),
            Members = memberData.toList(),
            Warikans = warikanData.toList()
        )
        rouletteState.value.Warikans.forEach { warikan ->
            sumOfProportion += warikan.proportion
        }
        _resultWarikanState.value =
            ResultWarikanFactory.create(rouletteState.value.Members)
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
                _isRotated.value = true
                viewModelScope.launch { _eventFlow.emit(UiEvent.StartRoulette) }
                _isRotatingState.value = true
                val warikans = _rouletteState.value.Warikans
                // 割り勘結果のindex取得
                val drawnIndex =
                    rouletteUseCases.getRouletteResultIndex(warikans, getCalendarStr())
                // 目的回転角度取得
                _resultDeg.value = rouletteUseCases.getResultDeg(warikans, drawnIndex)
                // 割り勘情報取得
                val ratios: List<Int> = warikans[drawnIndex].ratios.map { it.toInt() }
                resultPayments += rouletteUseCases.getWarikanResult(
                    _rouletteState.value.Total,
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
            is RoulettesEvent.RetryClickEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.Retry) }
            }
            is RoulettesEvent.GoHomeClickEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.GoHome) }
            }
        }
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