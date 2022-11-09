package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.InvalidWarikanException
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.warikan.WarikanUseCases
import jp.co.tbdeveloper.warikanapp.feature_roulette.parser.WarikanArrayType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_WARIKAN_NUM = 3
const val WARIKAN_MAX_NUM = 6

@HiltViewModel
class WarikanViewModel @Inject constructor(
    private val warikanUseCases: WarikanUseCases,
    private val settingsUseCases: SettingsUseCases,
    private val savedStateHandle: SavedStateHandle
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
    private val proportionState = _proportionState.asStateFlow()

    val isSave = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // load from navigation
        val memberData = savedStateHandle.get<Array<Member>>("members")
        total = savedStateHandle.get<String>("total") ?: ""
        if (memberData != null) {
            for (member in memberData) members.add(member)
        }
        // init warikan
        _warikanState.value =
            when (members.size) {
                2 -> listOf(
                    makeDefaultWarikan(listOf("1", "1")),
                    makeDefaultWarikan(listOf("1", "2")),
                    makeDefaultWarikan(listOf("2", "1")),
                )
                3 -> listOf(
                    makeDefaultWarikan(listOf("1", "1", "1")),
                    makeDefaultWarikan(listOf("2", "1", "1")),
                    makeDefaultWarikan(listOf("1", "2", "1")),
                    makeDefaultWarikan(listOf("1", "1", "2")),
                )
                4 -> listOf(
                    makeDefaultWarikan(listOf("1", "1", "1", "1")),
                    makeDefaultWarikan(listOf("2", "1", "1", "1")),
                    makeDefaultWarikan(listOf("1", "2", "1", "1")),
                    makeDefaultWarikan(listOf("1", "1", "2", "1")),
                    makeDefaultWarikan(listOf("1", "1", "1", "2")),
                )
                else -> listOf()
            }
        _proportionState.value = List(_warikanState.value.size) {
            "1"
        }
        lateinit var settings: Settings
        val job = CoroutineScope(Dispatchers.IO).launch {
            settings = SettingsFactory.create(settingsUseCases.getSettings())
        }
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        isSave.value = settings.autoSave
    }
    fun onEvent(event: WarikanEvent) {
        when (event) {
            is WarikanEvent.AddWarikanEvent -> {
                if (_warikanState.value.size >= WARIKAN_MAX_NUM) {
                    viewModelScope.launch {
                        _eventFlow.emit(UiEvent.AddError)
                    }
                    return
                }
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
            is WarikanEvent.StartEvent -> {
                viewModelScope.launch {
                    try {
                        warikanUseCases.warikanValidation(warikanState.value, proportionState.value)
                    } catch (e: InvalidWarikanException) {
                        _eventFlow.emit(UiEvent.InputError(0))
                        return@launch
                    }
                    _warikanState.value = _warikanState.value.mapIndexed { index, warikan ->
                        warikan.copy(proportion = _proportionState.value[index].toInt())
                    }
                    /* json encode */
                    val memberJson = Uri.encode(Gson().toJson(members))
                    val warikanData = warikanState.value.map {
                        it.copy(
                            proportion = if (warikanState.value.size == 5) 6
                            else 12 / warikanState.value.size
                        )
                    }
                    val warikanJson = Uri.encode(Gson().toJson(warikanData))
                    _eventFlow.emit(UiEvent.NextPage(total, isSave.value, memberJson, warikanJson))
                }
            }
            is WarikanEvent.LoadWarikansEvent -> {
                _warikanState.value = event.warikans
            }
        }
    }

    private fun makeDefaultWarikan(ratios: List<String> = List(members.size) { "1" }): Warikan {
        val color =
            if (ratios.any { it.isEmpty() or !it.isDigitsOnly() }) -1
            else if (ratios.all { it == ratios[0] }) -1
            else members[ratios.indexOf(ratios.maxBy { it.toInt() })].color

        return Warikan(
            ratios = ratios,
            proportion = 1,
            color = color
        )
    }

    sealed class UiEvent {
        object DeleteError : UiEvent()
        object AddError : UiEvent()
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