package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsEntityFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsFactory
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    val _autoSave = mutableStateOf(true)
    val autoSave: State<Boolean> = _autoSave

    private val _isMuted = mutableStateOf(true)
    val isMuted: State<Boolean> = _isMuted

    private val _setDarkTheme = mutableStateOf(0)
    val setDarkTheme: State<Int> = _setDarkTheme

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        lateinit var settings: Settings
        val job = CoroutineScope(Dispatchers.IO).launch {
            settings = SettingsFactory.create(settingsUseCases.getSettings())
        }
        while (!job.isCompleted) {
            Thread.sleep(100)
        }
        _autoSave.value = settings.autoSave
        _isMuted.value = settings.isMuted
        _setDarkTheme.value = settings.setDarkTheme
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.onAutoSaveChange -> {
                _autoSave.value = event.flg
            }
            is SettingsEvent.onIsMutedChange -> {
                _isMuted.value = event.flg
            }
            is SettingsEvent.onSetDarkThemeSelect -> {
                _setDarkTheme.value = event.value
            }
            is SettingsEvent.onSave -> {
                viewModelScope.launch {
                    settingsUseCases.updateSettings(
                        SettingsEntityFactory.create(
                            Settings(_autoSave.value, _isMuted.value, _setDarkTheme.value)
                        )
                    )
                    DarkThemeValHolder.isDarkThemeSelectIndex.value = _setDarkTheme.value
                    _eventFlow.emit(UiEvent.SaveEvent)
                }
            }
        }
    }
}

sealed class UiEvent {
    object SaveEvent : UiEvent()
}