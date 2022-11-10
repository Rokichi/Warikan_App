package com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis

sealed class Screen(val route: String) {
    object MemberScreen : Screen("MemberScreen")
    object WarikanScreen : Screen("WarikanScreen")
    object RouletteScreen : Screen("RouletteScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object MemberHistoryScreen : Screen("MemberHistoryScreen")
    object WarikanHistoryScreen : Screen("MemberHistoryScreen")
    object LicenceScreen : Screen("LicenceScreen")
}
