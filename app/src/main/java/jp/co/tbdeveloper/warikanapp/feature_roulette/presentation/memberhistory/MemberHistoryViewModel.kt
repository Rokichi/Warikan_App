package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberHistoryViewModel @Inject constructor(
    memberUseCases: MemberUseCases
) : ViewModel() {

    private val _memberHistoriesState = MutableStateFlow(listOf<List<Member>>())
    val memberHistoriesState = _memberHistoriesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        lateinit var membersData: List<List<Member>>
        val job = CoroutineScope(Dispatchers.IO).launch {
            membersData = MemberFactory.create(memberUseCases.getAllMembers())
        }
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        _memberHistoriesState.value = membersData
    }

    fun onEvent(event: MemberHistoryEvent) {
        when (event) {
            is MemberHistoryEvent.OnItemClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ItemSelected(_memberHistoriesState.value[event.index]))
                }
            }
        }
    }

    sealed class UiEvent {
        data class ItemSelected(val members: List<Member>) : UiEvent()
    }
}