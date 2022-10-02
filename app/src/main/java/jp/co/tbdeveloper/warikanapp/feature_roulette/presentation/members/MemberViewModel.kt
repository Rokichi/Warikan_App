package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


const val DEFAULT_MEMBER_NUM = 2
const val MAX_MEMBER_NUM = 3

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases
) : ViewModel() {
    var rouletteId = 0

    // メンバー
    private val _memberState = MutableStateFlow(
        MutableList(DEFAULT_MEMBER_NUM) { i ->
            Member("", i)
        }
    )
    val memberState = _memberState.asStateFlow()

    // 合計金額
    private val _totalState = mutableStateOf(0)
    val totalState = _totalState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getRouletteJob: Job? = null

    fun onEvent(event: MembersEvent) {
        when (event) {
            is MembersEvent.AddMembers -> {
                if (memberState.value.size < MAX_MEMBER_NUM) {
                    _memberState.value.add(
                        Member("", memberState.value.size)
                    )
                }
            }
            is MembersEvent.SaveRoulette -> {
                viewModelScope.launch {
                    val rouletteEntity = RouletteFactory.create(total = totalState.value)
                    rouletteId = rouletteEntity.id
                    val memberEntities =
                        MemberFactory.create(rouletteId = rouletteId, members = memberState.value)
                    // save
                    rouletteUseCases.addRoulette(rouletteEntity)
                    memberUseCases.addMember(memberEntities)
                }
            }
        }
    }

    sealed class UiEvent {
        object SaveRoulette : UiEvent()
    }

    private fun getRoulettes() {
        getRouletteJob?.cancel()
        rouletteUseCases.getRoulettes().launchIn(viewModelScope)
    }
}