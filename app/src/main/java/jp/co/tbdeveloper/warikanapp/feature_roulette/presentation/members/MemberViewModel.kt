package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidMemberException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidRouletteException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.RouletteEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


const val DEFAULT_MEMBER_NUM = 2
const val MAX_MEMBER_NUM = 3

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases
) : ViewModel() {

    // 不使用色データ
    private val unusedColorNums = MutableList(Member.memberColors.size) { it }

    // メンバー
    private val _memberState = MutableStateFlow(
        MutableList(DEFAULT_MEMBER_NUM) { i ->
            Member("", unusedColorNums.removeAt(Random.nextInt(unusedColorNums.size)))
        }
    )
    val memberState = _memberState.asStateFlow()

    // 合計金額
    private val _totalState = mutableStateOf("")
    val totalState = _totalState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: MemberEvent) {
        when (event) {
            is MemberEvent.AddMemberEvent -> {
                if (memberState.value.size < MAX_MEMBER_NUM) {
                    _memberState.value = (_memberState.value + Member(
                        "",
                        unusedColorNums.removeAt(Random.nextInt(unusedColorNums.size))
                    )) as MutableList<Member>
                }
            }

            is MemberEvent.EditTotalEvent -> {
                _totalState.value = event.value
            }

            is MemberEvent.EditMemberEvent -> {
                _memberState.value = _memberState.value.mapIndexed { index, member ->
                    if (index == event.index) Member(event.value, member.color)
                    else member
                } as MutableList<Member>
            }

            is MemberEvent.DeleteMemberEvent -> {
                if (_memberState.value.size > 2) {
                    unusedColorNums.add(_memberState.value[event.index].color)
                    _memberState.value = _memberState.value.filterIndexed { index, _ ->
                        index != event.index
                    } as MutableList<Member>
                    return
                }
                viewModelScope.launch { _eventFlow.emit(UiEvent.DeleteError) }
            }

            is MemberEvent.NextPageEvent -> {
                viewModelScope.launch {
                    // 入力金額が不正
                    try{
                        rouletteUseCases.rouletteValidation(_totalState.value)
                    }
                    catch (e: InvalidRouletteException){
                        _eventFlow.emit(UiEvent.InputError(0))
                        return@launch
                    }

                    // メンバー名が不正
                    try{
                        memberUseCases.memberValidation(_memberState.value)
                    }
                    catch (e:InvalidMemberException){
                        _eventFlow.emit(UiEvent.InputError(1))
                        return@launch
                    }
                    val rouletteEntity = RouletteEntityFactory.create(total = totalState.value.toInt())
                    val rouletteId = rouletteUseCases.addRoulette(rouletteEntity)
                    val memberEntities =
                        MemberEntityFactory.create(rouletteId = rouletteId, members = memberState.value)
                    memberUseCases.addMember(memberEntities)
                    _eventFlow.emit(UiEvent.NextPage(rouletteId))
                }
            }
        }
    }

    sealed class UiEvent {
        object DeleteError : UiEvent()
        data class InputError(val errorNum: Int) : UiEvent()
        data class NextPage(val id: Long) : UiEvent()
    }
}