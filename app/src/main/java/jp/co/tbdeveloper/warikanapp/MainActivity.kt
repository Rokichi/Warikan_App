package jp.co.tbdeveloper.warikanapp

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.Warikans.components.WarikansScreen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.components.MembersScreen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.ui.theme.WarikanAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            WarikanAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = Screen.MemberScreen.route
                        ) {
                            composable(route = Screen.MemberScreen.route) {
                                val viewModel: MemberViewModel = hiltViewModel()
                                MembersScreen(navController, viewModel)
                            }

                            composable(
                                route = Screen.WarikanScreen.route + "/{id}",
                                arguments = listOf(
                                    navArgument("id") { type = NavType.LongType }
                                )
                            ) { backStackEntry ->
                                val id = backStackEntry.arguments?.getLong("id") ?: 0
                                val viewModel: MemberViewModel = hiltViewModel()
                                WarikansScreen(viewModel, id)
                            }

                        }
                        // admob
                        AndroidView(
                            modifier = Modifier.fillMaxWidth(),
                            factory = { context ->
                                val adView = AdView(context)
                                val displayMetrics = Resources.getSystem().displayMetrics
                                val width =
                                    (displayMetrics.widthPixels / displayMetrics.density).toInt()
                                adView.setAdSize(
                                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                                        context,
                                        width
                                    )
                                )
                                adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
                                adView.loadAd(AdRequest.Builder().build())
                                adView
                            },
                        )
                    }
                }
            }
        }
        MobileAds.initialize(this) {}
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WarikanAppTheme {
        Greeting("Android")
    }
}