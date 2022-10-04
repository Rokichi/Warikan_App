package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
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
    private val unusedColorNums = MutableList(Member.memberColors.size) { it }

    // メンバー
    private val _memberState = MutableStateFlow(
        MutableList(DEFAULT_MEMBER_NUM) { i ->
            Member("", unusedColorNums.removeAt(unusedColorNums.random()))
        }
    )
    val memberState = _memberState.asStateFlow()

    // 合計金額
    private val _totalState = mutableStateOf("")
    val totalState = _totalState


    /*
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var getRouletteJob: Job? = null
    */



    fun onEvent(event: MemberEvent) {
        when (event) {
            is MemberEvent.AddMember -> {
                if (memberState.value.size < MAX_MEMBER_NUM) {
                    _memberState.value = (_memberState.value + Member(
                        "",
                        unusedColorNums.removeAt(unusedColorNums.random())
                    )) as MutableList<Member>
                }
            }

            is MemberEvent.EditTotalEvent -> {
                _totalState.value = event.value
            }

            is MemberEvent.EditMemberEvent -> {
                _memberState.value = _memberState.value.mapIndexed{ index, member ->
                    if(index == event.index) Member(event.value, member.color)
                    else member
                } as MutableList<Member>
            }

            is MemberEvent.DeleteMemberEvent -> {
                unusedColorNums.add(_memberState.value[event.index].color)
                _memberState.value = _memberState.value.filterIndexed { index, _ ->
                    index != event.index
                } as MutableList<Member>
            }

            is MemberEvent.SaveRoulette -> {
                viewModelScope.launch {
                    val rouletteEntity = RouletteFactory.create(total = totalState.value.toInt())
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

    fun showState() {
        for(num in unusedColorNums){
            Log.i("$unusedColorNums.size", "$num")
        }
        for (member in memberState.value) {
            Log.i("member", member.name)
        }
        Log.i("total", totalState.value)
    }

    /*
    sealed class UiEvent {
        object SaveRoulette : UiEvent()
    }

    private fun getRoulettes() {
        getRouletteJob?.cancel()
        rouletteUseCases.getRoulettes().launchIn(viewModelScope)
    }
     */
}