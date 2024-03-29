package com.tbdeveloper.warikanapp

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
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import com.tbdeveloper.warikanapp.feature_roulette.parser.MemberArrayType
import com.tbdeveloper.warikanapp.feature_roulette.parser.WarikanArrayType
import com.tbdeveloper.warikanapp.feature_roulette.presentation.license.components.LicencePage
import com.tbdeveloper.warikanapp.feature_roulette.presentation.member_history.components.MemberHistoryScreen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.members.components.MembersScreen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components.RouletteScreen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components.SettingScreen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.component.WarikanHistoryScreen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components.WarikansScreen
import com.tbdeveloper.warikanapp.ui.theme.WarikanAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var mInterstitialAd: InterstitialAd? = null
    var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
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
                                val members =
                                    navController.currentBackStackEntry!!.savedStateHandle.get<Array<Member>>(
                                        "members"
                                    )
                                MembersScreen(navController, members)
                            }

                            composable(route = Screen.MemberHistoryScreen.route) {
                                MemberHistoryScreen(navController) {
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("members", it)
                                    navController.popBackStack()
                                }
                            }

                            composable(
                                route = Screen.WarikanHistoryScreen.route + "/{members}",
                                arguments = listOf(
                                    navArgument("members") { type = MemberArrayType() },
                                )
                            ) {
                                WarikanHistoryScreen(navController) {
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("warikans", it)
                                    navController.popBackStack()
                                }
                            }

                            composable(
                                route = Screen.WarikanScreen.route + "/{members}/{total}",
                                arguments = listOf(
                                    navArgument("members") { type = MemberArrayType() },
                                    navArgument("total") { type = NavType.StringType },
                                )
                            ) {
                                val warikans =
                                    navController.currentBackStackEntry!!.savedStateHandle.get<Array<Warikan>>(
                                        "warikans"
                                    )
                                WarikansScreen(navController, warikans)
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
                                            "ca-app-pub-1543593285163877/9224468573",
                                        ) { onComplete() }
                                    },
                                    showInterstitialAd = { popUpPage: () -> Unit ->
                                        showInterstitialAd(
                                            mInterstitialAd,
                                        ) {
                                            popUpPage()
                                            mInterstitialAd = null
                                        }
                                    },
                                    initializeRewardedAd = { onComplete: () -> Unit ->
                                        initializeRewardedAd(
                                            "ca-app-pub-1543593285163877/3588998515"
                                        ) { onComplete() }
                                    },
                                    showRewardedAd = { popUpPage: () -> Unit ->
                                        showRewardedAd(
                                            mRewardedAd,
                                        ) {
                                            popUpPage()
                                            mRewardedAd = null
                                        }
                                    }
                                )
                            }
                            composable(route = Screen.SettingsScreen.route) {
                                SettingScreen(navController)
                            }
                            composable(route = Screen.LicenceScreen.route) {
                                LicencePage(navController)
                            }
                        }
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
                                adView.adUnitId = "ca-app-pub-1543593285163877/7978550108"
                                adView.loadAd(AdRequest.Builder().build())
                                adView
                            },
                        )
                    }
                }
            }
        }
    }

    private fun initializeInterstitialAd(id: String, onComplete: () -> Unit) {
        if (mInterstitialAd != null) return
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
            mInterstitialAd.show(this)
        }
    }

    private fun initializeRewardedAd(id: String, onComplete: () -> Unit) {
        if (mRewardedAd != null) return
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
            mRewardedAd.show(this, OnUserEarnedRewardListener() {
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