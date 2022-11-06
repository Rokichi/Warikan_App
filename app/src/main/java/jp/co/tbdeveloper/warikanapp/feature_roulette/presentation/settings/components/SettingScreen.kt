package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.SettingViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.SettingsEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.UiEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.ShadowButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingScreen(
    navController: NavController,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SaveEvent -> {
                    navController.navigate(Screen.MemberScreen.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    val autoSave = remember { viewModel.autoSave }
    val isMuted = remember { viewModel.isMuted }
    val setDarkTheme = remember { viewModel.setDarkTheme }

    Column(
        // 一回り小さく配置
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        // トップバー
        PageBackBar {
            navController.navigate(Screen.MemberScreen.route) {
                popUpTo(0)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // 自動保存
            TextAndSwitch(text = "自動保存", isChecked = autoSave) { flg: Boolean ->
                viewModel.onEvent(SettingsEvent.onAutoSaveChange(flg))
            }

            // ミュート
            TextAndSwitch(text = "ミュート", isChecked = isMuted) { flg: Boolean ->
                viewModel.onEvent(SettingsEvent.onIsMutedChange(flg))
            }

            // テーマのプルダウン
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                ShadowButton(
                    text = "保存",
                    modifier = Modifier.weight(2.0f),
                    padding = 80,
                    backGroundColor = MaterialTheme.colors.primary,
                    borderColor = MaterialTheme.colors.background,
                    textStyle = MaterialTheme.typography.body1,
                    offsetY = 9.dp,
                    offsetX = 0.dp,
                    onClick = {
                        viewModel.onEvent(SettingsEvent.onSave)
                    }
                )
            }
        }
    }
}

@Composable
fun PageBackBar(
    onPageBackButtonClick: () -> Unit = {}
) {
    Row(
        // 横幅Max, 横は等間隔，縦は真ん中に
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, radius = 18.dp),
                    onClick = { onPageBackButtonClick() }
                ),
            contentDescription = "page back button"
        )
    }
}


