package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MembersState())
    val state: State<MembersState> = _state

    private var getMemberJob: Job? = null

    fun onEvent(event: MembersEvent) {
        when (event) {
            /*
            is MembersEvent.DeleteMember -> {
                viewModelScope.launch {
                    memberUseCases.deleteMember(event.roulette)
                }
            }
             */
        }
    }

    private fun getMembers(id: Int) {
        getMemberJob?.cancel()
        viewModelScope.launch {
            memberUseCases.getMembersById(id)
        }
    }
}