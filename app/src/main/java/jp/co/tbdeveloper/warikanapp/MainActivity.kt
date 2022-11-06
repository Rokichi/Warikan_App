package jp.co.tbdeveloper.warikanapp

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import jp.co.tbdeveloper.warikanapp.feature_roulette.parser.MemberArrayType
import jp.co.tbdeveloper.warikanapp.feature_roulette.parser.WarikanArrayType
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.components.MembersScreen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components.RouletteScreen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components.SettingScreen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components.WarikansScreen
import jp.co.tbdeveloper.warikanapp.ui.theme.WarikanAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var mInterstitialAd: InterstitialAd? = null
    var mRewardedAd: RewardedAd? = null

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
                                MembersScreen(navController)
                            }

                            composable(
                                route = Screen.WarikanScreen.route + "/{members}/{total}",
                                arguments = listOf(
                                    navArgument("members") { type = MemberArrayType() },
                                    navArgument("total") { type = NavType.StringType },
                                )
                            ) {
                                WarikansScreen(navController)
                            }

                            composable(
                                route = Screen.RouletteScreen.route + "/{total}/{isSave}/{members}/{warikans}",
                                arguments = listOf(
                                    navArgument("total") { type = NavType.StringType },
                                    navArgument("isSave") { type = NavType.BoolType },
                                    navArgument("members") { type = MemberArrayType() },
                                    navArgument("warikans") { type = WarikanArrayType() },
                                )
                            ) {
                                RouletteScreen(
                                    navController = navController,
                                    initializeInterstitialAd = { onComplete: () -> Unit ->
                                        initializeInterstitialAd(
                                            "ca-app-pub-3940256099942544/1033173712",
                                        ) { onComplete() }
                                    },
                                    showInterstitialAd = { popUpPage: () -> Unit ->
                                        showInterstitialAd(
                                            mInterstitialAd,
                                        ) { popUpPage() }
                                    },
                                    initializeRewardedAd = { onComplete: () -> Unit ->
                                        initializeRewardedAd(
                                            "ca-app-pub-3940256099942544/5224354917"
                                        ) { onComplete() }
                                    },
                                    showRewardedAd = { popUpPage: () -> Unit ->
                                        showRewardedAd(
                                            mRewardedAd,
                                        ) { popUpPage() }
                                    }
                                )
                            }
                            composable(route = Screen.SettingsScreen.route) {
                                SettingScreen(navController)
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

    private fun initializeInterstitialAd(id: String, onComplete: () -> Unit) {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            id,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Toast.makeText(context, "広告のロードに失敗しました 再度お試し下さい", Toast.LENGTH_SHORT).show()
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("AsMob", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                onComplete()
            }

            override fun onAdDismissedFullScreenContent() {
                onComplete()
            }
        }
    }

    private fun showInterstitialAd(mInterstitialAd: InterstitialAd?, popUpPage: () -> Unit) {
        if (mInterstitialAd == null) {
            popUpPage()
        } else {
            mInterstitialAd?.show(this)
        }
    }

    private fun initializeRewardedAd(id: String, onComplete: () -> Unit) {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, id, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d("AdMob", "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                onComplete()
            }
        }
    }

    private fun showRewardedAd(mRewardedAd: RewardedAd?, popUpPage: () -> Unit) {
        if (mRewardedAd == null) {
            popUpPage()
        } else {
            mRewardedAd?.show(this, OnUserEarnedRewardListener() {
                popUpPage()
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WarikanAppTheme {
    }
}