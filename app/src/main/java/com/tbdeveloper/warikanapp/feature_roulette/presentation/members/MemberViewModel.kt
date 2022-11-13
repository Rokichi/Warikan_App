package com.tbdeveloper.warikanapp.feature_roulette.presentation.members

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.tbdeveloper.warikanapp.DarkThemeValHolder
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidMemberException
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidRouletteException
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsFactory
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.roulette.RouletteUseCases
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import com.tbdeveloper.warikanapp.feature_roulette.utils.getMD5HashInt
import com.tbdeveloper.warikanapp.feature_roulette.utils.getMillisTimeStr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


const val DEFAULT_MEMBER_NUM = 2
const val MAX_MEMBER_NUM = 4

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val rouletteUseCases: RouletteUseCases,
    private val memberUseCases: MemberUseCases,
    private val settingsUseCases: SettingsUseCases,
) : ViewModel() {

    // 不使用色データ
    private val unUsedColorNums =
        MutableList(Member.memberColors(DarkThemeValHolder.isDarkTheme.value).size) { it }
    private var hashLongNum: Int = getMD5HashInt(getMillisTimeStr())

    lateinit var settings: Settings

    // メンバー
    private val _memberState = MutableStateFlow(mutableListOf<Member>())
    val memberState = _memberState.asStateFlow()

    init {
        // load theme settings
        val job = CoroutineScope(Dispatchers.IO).launch {
            settings = SettingsFactory.create(settingsUseCases.getSettings())
        }
        // wait
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        if (_memberState.value.size == 0) {
            _memberState.value = MutableList(DEFAULT_MEMBER_NUM) {
                Member(
                    "",
                    unUsedColorNums.removeAt(updateHashLongNum() % unUsedColorNums.size)
                )
            }
        }
        DarkThemeValHolder.isDarkThemeSelectIndex.value = settings.setDarkTheme
    }


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
                        unUsedColorNums.removeAt(updateHashLongNum() % unUsedColorNums.size)
                    )) as MutableList<Member>
                } else viewModelScope.launch { _eventFlow.emit(UiEvent.AddMemberError) }

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
                    unUsedColorNums.add(_memberState.value[event.index].color)
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
                    try {
                        rouletteUseCases.rouletteValidation(_totalState.value)
                    } catch (e: InvalidRouletteException) {
                        _eventFlow.emit(UiEvent.InputError(0))
                        return@launch
                    }

                    // メンバー名が不正
                    try {
                        memberUseCases.memberValidation(_memberState.value)
                    } catch (e: InvalidMemberException) {
                        _eventFlow.emit(UiEvent.InputError(1))
                        return@launch
                    }
                    /* json encode */
                    val memberJson = Uri.encode(Gson().toJson(memberState.value))
                    _eventFlow.emit(UiEvent.NextPage(memberJson, totalState.value))
                }
            }
            is MemberEvent.LoadMemberEvent -> {
                // initialize colors
                for (member in _memberState.value) {
                    unUsedColorNums.add(member.color)
                }
                _memberState.value = event.members.toMutableList()
                for (member in _memberState.value) {
                    unUsedColorNums.removeAt(unUsedColorNums.indexOf(member.color))
                }
            }
        }
    }

    fun updateHashLongNum(): Int {
        this.hashLongNum = getMD5HashInt(hashLongNum.toString())
        return this.hashLongNum
    }

    sealed class UiEvent {
        object DeleteError : UiEvent()
        object AddMemberError : UiEvent()
        data class InputError(val errorNum: Int) : UiEvent()
        data class NextPage(val members: String?, val total: String) : UiEvent()
    }
}
