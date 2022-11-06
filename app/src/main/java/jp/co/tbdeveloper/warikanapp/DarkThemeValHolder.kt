package jp.co.tbdeveloper.warikanapp

import androidx.compose.runtime.mutableStateOf

class DarkThemeValHolder {
    companion object {
        val isDarkThemeSelectIndex = mutableStateOf(0)
        val isDarkTheme = mutableStateOf(false)
    }
}