package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis

sealed class Screen(val route:String){
    object MemberScreen: Screen("MemberScreen")
    object WarikanScreen: Screen("WarikanScreen")
}
