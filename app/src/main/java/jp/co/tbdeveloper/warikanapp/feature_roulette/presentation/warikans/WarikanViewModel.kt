package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidWarikanException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.WarikanEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.member.MemberUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    var members = listOf<Member>()

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
    val rouletteId = mutableStateOf(0.toLong())

    init {
        savedStateHandle.get<Long>("id")?.let { id ->
            rouletteId.value = id
            CoroutineScope(Dispatchers.IO).launch {
                val memberEntitys = memberUseCases.getMembersById(id)
                if (memberEntitys == null) return@launch
                members = MemberFactory.create(memberEntitys)
                // init warikan
                _warikanState.value = List(DEFAULT_WARIKAN_NUM) {
                    makeDefaultWarikan()
                }
                _proportionState.value = List(DEFAULT_WARIKAN_NUM) {
                    "1"
                }
            }
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
                    warikanUseCases.addWarikan(
                        WarikanEntityFactory.create(
                            rouletteId.value,
                            _warikanState.value,
                            _proportionState.value
                        )
                    )
                    _eventFlow.emit(UiEvent.NextPage(rouletteId.value, isSave.value))
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
        data class NextPage(val id: Long, val isSave: Boolean) : UiEvent()
    }
}