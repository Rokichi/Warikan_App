package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.ResultWarikanFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    private val _resultDeg = mutableStateOf(0.0f)
    val resultDeg: State<Float> = _resultDeg

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<Long>("id")?.let { id ->
            Log.i("id", id.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val rouletteEntity = rouletteUseCases.getRouletteById(id)
                val memberEntitys = memberUseCases.getMembersById(id)
                val warikanEntitys = warikanUseCases.getWarikansById(id)
                if (rouletteEntity == null) return@launch
                if (memberEntitys == null) return@launch
                if (warikanEntitys == null) return@launch
                _rouletteState.value = RouletteFactory.create(
                    rouletteEntity,
                    MemberFactory.create(memberEntitys),
                    WarikanFactory.create(warikanEntitys)
                )
                rouletteState.value.Warikans.forEach { warikan ->
                    sumOfProportion += warikan.proportion
                }
                _resultWarikanState.value =
                    ResultWarikanFactory.create(rouletteState.value.Members)
            }
        }
    }

    fun onEvent(event: RoulettesEvent) {
        when (event) {
            is RoulettesEvent.StartClickEvent -> {
                _isRotatingState.value = true
                // 割り勘結果を計算
                _resultDeg.value = 420f
            }

            is RoulettesEvent.StopClickEvent -> {
                _isRotatingState.value = false
            }
            is RoulettesEvent.EndRouletteEvent -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.EndRoulette) }
            }
        }
    }

    sealed class UiEvent {
        object StartRoulette : UiEvent()
        object StopRoulette : UiEvent()
        object EndRoulette : UiEvent()
    }

    fun getRoulette() {
        /*
        getRouletteJob?.cancel()
        rouletteUseCases.getRoulettes().launchIn(viewModelScope)
         */
    }
}