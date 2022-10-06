package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases,
    private val warikanUseCases: WarikanUseCases,
) : ViewModel() {

    var rouletteState = mutableStateOf(Roulette())

    // 割り勘結果
    private val _resultWarikanState = MutableStateFlow<List<ResultWarikan>>(
        mutableListOf()
    )
    val resultWarikanState = _resultWarikanState.asStateFlow()

    var sumOfProprotion = 0

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getRouletteJob: Job? = null

    fun onEvent(event: RoulettesEvent) {
        when (event) {
            is RoulettesEvent.InitRouletteEvent -> {
                viewModelScope.launch {
                    val rouletteEntity = rouletteUseCases.getRouletteById(event.id)
                    val memberEntitys = memberUseCases.getMembersById(event.id)
                    val warikanEntitys = warikanUseCases.getWarikansById(event.id)
                    if (rouletteEntity == null) return@launch
                    if (memberEntitys == null) return@launch
                    if (warikanEntitys == null) return@launch
                    rouletteState.value = RouletteFactory.create(
                        rouletteEntity,
                        MemberFactory.create(memberEntitys),
                        WarikanFactory.create(warikanEntitys)
                    )
                    rouletteState.value.Warikans.forEach { warikan ->
                        sumOfProprotion += warikan.proportion
                    }
                }
            }
            is RoulettesEvent.StartClickEvent -> {
                // 割り勘結果を計算
            }
        }
    }

    sealed class UiEvent {
        data class StartRoulette(val resultDeg: Float)
    }

    private fun getRoulettes() {
        getRouletteJob?.cancel()
        rouletteUseCases.getRoulettes().launchIn(viewModelScope)
    }
}