package com.tbdeveloper.warikanapp.feature_roulette.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbdeveloper.warikanapp.DarkThemeValHolder
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsEntityFactory
import com.tbdeveloper.warikanapp.feature_roulette.domain.repository.SettingsFactory
import com.tbdeveloper.warikanapp.feature_roulette.domain.use_case.settings.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val _autoSave = mutableStateOf(false)
    val autoSave: State<Boolean> = _autoSave

    private val _isMuted = mutableStateOf(false)
    val isMuted: State<Boolean> = _isMuted

    private val _isApproximate = mutableStateOf(false)
    val isApproximate: State<Boolean> = _isApproximate

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
        _isApproximate.value = settings.isApproximate
        _setDarkTheme.value = settings.setDarkTheme
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnAutoSaveChange -> {
                _autoSave.value = event.flg
            }
            is SettingsEvent.OnIsMutedChange -> {
                _isMuted.value = event.flg
            }
            is SettingsEvent.OnIsApproximateChange -> {
                _isApproximate.value = event.flg
            }
            is SettingsEvent.OnSetDarkThemeSelect -> {
                _setDarkTheme.value = event.value
            }
            is SettingsEvent.OnSave -> {
                viewModelScope.launch {
                    settingsUseCases.updateSettings(
                        SettingsEntityFactory.create(
                            Settings(
                                _autoSave.value,
                                _isMuted.value,
                                _isApproximate.value,
                                _setDarkTheme.value
                            )
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