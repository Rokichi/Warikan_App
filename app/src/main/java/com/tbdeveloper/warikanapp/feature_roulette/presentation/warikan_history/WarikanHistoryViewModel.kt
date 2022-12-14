package com.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanFactory
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarikanHistoryViewModel @Inject constructor(
    warikanUseCases: WarikanUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _warikanHistoriesState = MutableStateFlow(listOf<List<Warikan>>())
    val warikanHistoriesState = _warikanHistoriesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var members: List<Member>

    init {
        // load from navigation
        val memberData = savedStateHandle.get<Array<Member>>("members")
        members = memberData?.toList() ?: listOf()
        lateinit var warikansData: List<List<Warikan>>
        val job = CoroutineScope(Dispatchers.IO).launch {
            warikansData =
                WarikanFactory.create(members.size.toLong(), warikanUseCases.getAllWarikans())
        }
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        _warikanHistoriesState.value = warikansData
    }

    fun onEvent(event: WarikanHistoryEvent) {
        when (event) {
            is WarikanHistoryEvent.OnItemClick -> {
                val warikanData = _warikanHistoriesState.value[event.index]
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ItemSelected(warikanData.toTypedArray()))
                }
            }
        }
    }

    sealed class UiEvent {
        data class ItemSelected(val warikans: Array<Warikan>) : UiEvent()
    }
}