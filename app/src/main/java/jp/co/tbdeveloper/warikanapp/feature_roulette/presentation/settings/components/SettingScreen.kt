package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Settings
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.SettingViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.SettingsEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.UiEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.NoRippleDropdownMenuItem
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
                    navController.navigateUp()
                }
            }
        }
    }

    val autoSave = remember { viewModel.autoSave }
    val isMuted = remember { viewModel.isMuted }
    val setDarkTheme = remember { viewModel.setDarkTheme }
    val expanded = remember { mutableStateOf(false) }

    Column(
        // 一回り小さく配置
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        // トップバー
        PageBackBar {
            navController.navigateUp()
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.clickable(onClick = { expanded.value = true }),
                    text = "ダークテーマ",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.surface,
                )
                // テーマのプルダウン
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
                        .clickable { expanded.value = true }
                ) {
                    Row {
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = Settings.darkThemeText[setDarkTheme.value],
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.surface,
                        )
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(top = 5.dp),
                            painter = painterResource(
                                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_arrow_down_dark
                                else R.drawable.ic_arrow_down_light
                            ),
                            contentScale = ContentScale.Inside,
                            contentDescription = "drop down button"
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.background(MaterialTheme.colors.background),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        Settings.darkThemeText.forEachIndexed { index, _ ->
                            NoRippleDropdownMenuItem(
                                onClick = {
                                    expanded.value = false
                                    viewModel.onEvent(SettingsEvent.onSetDarkThemeSelect(index))
                                }
                            ) {
                                Text(
                                    text = Settings.darkThemeText[index],
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.surface,
                                )
                            }
                        }
                    }
                }
            }
            // 保存ボタン
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
            painter = painterResource(
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_arrow_left_dark
                else R.drawable.ic_arrow_left_light
            ),
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


