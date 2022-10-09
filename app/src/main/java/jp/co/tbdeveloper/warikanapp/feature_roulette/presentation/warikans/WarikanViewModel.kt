package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.MemberFactory
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
    private var members = listOf<Member>()

    // 割り勘
    private val _warikanState = MutableStateFlow(
        mutableListOf<Warikan>()
    )
    val warikanState = _warikanState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Long>("id")?.let { id ->
            CoroutineScope(Dispatchers.IO).launch {
                val memberEntitys = memberUseCases.getMembersById(id)
                if (memberEntitys == null) return@launch
                members = MemberFactory.create(memberEntitys)
            }

            // init warikan
            _warikanState.value = MutableList(DEFAULT_WARIKAN_NUM) {
                makeDefaultWarikan()
            }
        }
    }

    fun onEvent(event: WarikanEvent) {
        when (event) {
            is WarikanEvent.AddWarikanEvent -> {
                _warikanState.value =
                    (_warikanState.value + makeDefaultWarikan()) as MutableList<Warikan>
            }
            is WarikanEvent.DeleteWarikanEvent -> {
                if (_warikanState.value.size > 2) {
                    _warikanState.value = _warikanState.value.filterIndexed { index, _ ->
                        index != event.index
                    } as MutableList<Warikan>
                    return
                }
                viewModelScope.launch { _eventFlow.emit(UiEvent.DeleteError) }
            }
            is WarikanEvent.EditWarikanEvent -> {}
            is WarikanEvent.EditProportionEvent -> {}
            is WarikanEvent.StartEvent -> {}
        }
    }

    private fun makeDefaultWarikan(): Warikan {
        val defRatios = List(members.size) { (10 / members.size).toString() }
        val ratios = defRatios.joinToString(":")
        return Warikan(
            ratios = ratios,
            proportion = 1,
            color = -1
        )
    }

    sealed class UiEvent {
        object DeleteError : UiEvent()
        data class InputError(val errorNum: Int) : UiEvent()
        data class NextPage(val id: Long) : UiEvent()
    }
}