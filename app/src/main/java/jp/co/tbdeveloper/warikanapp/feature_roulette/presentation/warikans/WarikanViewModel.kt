package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidWarikanException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_WARIKAN_NUM = 2

@HiltViewModel
class WarikanViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases,
    private val warikanUseCases: WarikanUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // メンバー
    var members = mutableListOf<Member>()
    var total = ""

    // 割り勘
    private val _warikanState = MutableStateFlow(
        listOf<Warikan>()
    )
    val warikanState = _warikanState.asStateFlow()

    // 割り勘
    private val _proportionState = MutableStateFlow(
        listOf<String>()
    )
    val proportionState = _proportionState.asStateFlow()

    val isSave = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // load from navigation
        val memberData = savedStateHandle.get<Array<Member>>("members")
        total = savedStateHandle.get<String>("total") ?: ""
        if (memberData != null) {
            for (member in memberData) members.add(member)
        }// init warikan
        _warikanState.value = List(DEFAULT_WARIKAN_NUM) {
            makeDefaultWarikan()
        }
        _proportionState.value = List(DEFAULT_WARIKAN_NUM) {
            "1"
        }
    }

    fun onEvent(event: WarikanEvent) {
        when (event) {
            is WarikanEvent.AddWarikanEvent -> {
                _warikanState.value =
                    (_warikanState.value + makeDefaultWarikan())
                _proportionState.value = (_proportionState.value + "1")

            }
            is WarikanEvent.DeleteWarikanEvent -> {
                if (_warikanState.value.size > 2) {
                    _warikanState.value = _warikanState.value.filterIndexed { index, _ ->
                        index != event.index
                    }
                    _proportionState.value = _proportionState.value.filterIndexed { index, _ ->
                        index != event.index
                    }
                    return
                }
                viewModelScope.launch { _eventFlow.emit(UiEvent.DeleteError) }
            }
            is WarikanEvent.EditWarikanEvent -> {
                _warikanState.value = _warikanState.value.mapIndexed { index, warikan ->
                    if (index == event.index) {
                        val ratios = warikan.ratios.mapIndexed { index, ratio ->
                            if (index == event.num) event.value
                            else ratio
                        }

                        warikan.copy(
                            color =
                            if (ratios.any { it.isEmpty() or !it.isDigitsOnly() }) -1
                            else if (ratios.all { it == ratios[0] }) -1
                            else members[ratios.indexOf(ratios.maxBy { it.toInt() })].color,
                            ratios = ratios
                        )
                    } else warikan
                }
            }
            is WarikanEvent.EditProportionEvent -> {
                _proportionState.value = _proportionState.value.mapIndexed() { index, proportion ->
                    if (index == event.index) {
                        event.value
                    } else proportion
                }
            }
            is WarikanEvent.StartEvent -> {
                viewModelScope.launch {
                    try {
                        warikanUseCases.warikanValidation(warikanState.value, proportionState.value)
                    } catch (e: InvalidWarikanException) {
                        _eventFlow.emit(UiEvent.InputError(0))
                        return@launch
                    }
                    /* json encode */
                    val memberJson = Uri.encode(Gson().toJson(members))
                    val warikanJson = Uri.encode(Gson().toJson(warikanState.value))
                    _eventFlow.emit(UiEvent.NextPage(total, isSave.value, memberJson, warikanJson))
                }
            }
        }
    }

    private fun makeDefaultWarikan(): Warikan {
        val defRatios = List(members.size) { "" }
        return Warikan(
            ratios = defRatios,
            proportion = 1,
            color = -1
        )
    }

    sealed class UiEvent {
        object DeleteError : UiEvent()
        data class InputError(val errorNum: Int) : UiEvent()
        data class NextPage(
            val total: String,
            val isSave: Boolean,
            val memberJson: String,
            val warikanJson: String
        ) :
            UiEvent()
    }
}