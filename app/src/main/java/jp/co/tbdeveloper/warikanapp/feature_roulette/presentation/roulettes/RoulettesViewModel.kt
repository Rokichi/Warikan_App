package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.Roulette
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.RouletteUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoulettesViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(RoulettesState())
    val state: State<RoulettesState> = _state

    private var recentlyDeleteRoulette: Roulette? = null

    private var getRouletteJob: Job? = null

    fun onEvent(event: RoulettesEvent) {
        when (event) {
            is RoulettesEvent.DeleteRoulette -> {
                viewModelScope.launch {
                    rouletteUseCases.deleteRoulette(event.roulette)
                    recentlyDeleteRoulette = event.roulette
                }
            }
            is RoulettesEvent.RestoreRoulette -> {
                viewModelScope.launch {
                    rouletteUseCases.addRoulette(recentlyDeleteRoulette ?: return@launch)
                    recentlyDeleteRoulette = null
                }
            }
        }
    }

    private fun getRoulettes(){
        getRouletteJob?.cancel()
        rouletteUseCases.getRoulettes().launchIn(viewModelScope)
    }
}