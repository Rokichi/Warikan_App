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

    var rotated = false
    private val _resultDeg = mutableStateOf(0.0f)
    val resultDeg: State<Float> = _resultDeg

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // load from navigation
        val total = savedStateHandle.get<String>("total") ?: 0
        val memberData = savedStateHandle.get<Array<Member>>("members") ?: arrayOf()
        val warikanData = savedStateHandle.get<Array<Warikan>>("warikans") ?: arrayOf()
        for(warikand in warikanData) Log.i("pro", warikand.proportion.toString())
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
    var ddd=0
    fun onEvent(event: RoulettesEvent) {
        when (event) {
            is RoulettesEvent.StartClickEvent -> {
                if(rotated) return
                _isRotatingState.value = true
                // 割り勘結果のindex取得
                val drawnIndex =
                    rouletteUseCases.getRouletteResultIndex(_rouletteState.value.Warikans, getCalendarStr())
                Log.i("drawnIndex", drawnIndex.toString())
                ddd = drawnIndex
                _resultDeg.value = rouletteUseCases.getResultDeg(_rouletteState.value.Warikans, drawnIndex)
                Log.i("resultDeg", _resultDeg.value.toString())
                viewModelScope.launch { _eventFlow.emit(UiEvent.StartRoulette) }
            }

            is RoulettesEvent.StopClickEvent -> {
                rotated = true
                _isRotatingState.value = false
                viewModelScope.launch { _eventFlow.emit(UiEvent.StopRoulette(ddd)) }
                //viewModelScope.launch { _eventFlow.emit(UiEvent.StopRoulette()) }
            }
            is RoulettesEvent.EndRouletteEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.EndRoulette) }
            }
        }
    }


    sealed class UiEvent {
        object SaveEvent : UiEvent()
        object StartRoulette : UiEvent()
        //object StopRoulette : UiEvent()
        data class StopRoulette(val re:Int) : UiEvent()
        object EndRoulette : UiEvent()
    }
}