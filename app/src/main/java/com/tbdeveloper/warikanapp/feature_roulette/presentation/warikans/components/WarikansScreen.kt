package com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tbdeveloper.warikanapp.DarkThemeValHolder
import com.tbdeveloper.warikanapp.R
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import com.tbdeveloper.warikanapp.feature_roulette.presentation.members.MAX_MEMBER_NUM
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.AutoResizeText
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.FontSizeRange
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.ShadowButton
import com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.WARIKAN_MAX_NUM
import com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.WarikanEvent
import com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.WarikanViewModel
import com.tbdeveloper.warikanapp.ui.theme.MainAccent
import com.tbdeveloper.warikanapp.ui.theme.SubAccent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WarikansScreen(
    navController: NavController,
    warikans: Array<Warikan>?,
    viewModel: WarikanViewModel = hiltViewModel(),
) {
    // 画面外のフォーカスを検知
    val focusManager = LocalFocusManager.current
    val warikanState = viewModel.warikanState.collectAsState()
    val isSave = remember { viewModel.isSave }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (!warikans.isNullOrEmpty()) {
            viewModel.onEvent(WarikanEvent.LoadWarikansEvent(warikans.toList()))
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WarikanViewModel.UiEvent.DeleteError -> {
                    Toast.makeText(context, "割り勘要素を2つ未満にすることはできません", Toast.LENGTH_SHORT).show()
                }
                is WarikanViewModel.UiEvent.AddError -> {
                    Toast.makeText(context, "割り勘要素は${WARIKAN_MAX_NUM}個までです", Toast.LENGTH_SHORT)
                        .show()
                }
                is WarikanViewModel.UiEvent.InputError -> {
                    when (event.errorNum) {
                        0 -> {
                            Toast.makeText(context, "数値を入力してください", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is WarikanViewModel.UiEvent.HistoryPage -> {
                    navController.navigate(Screen.WarikanHistoryScreen.route + "/${event.memberJson}")
                }
                is WarikanViewModel.UiEvent.NextPage -> {
                    navController.navigate(
                        Screen.RouletteScreen.route +
                                "/${event.total}/${event.isSave}/${event.memberJson}/${event.warikanJson}"
                    )
                }
            }
        }
    }
    Column(
        // 一回り小さく配置
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // トップバー
        PageBackAndHistoryBar(
            onPageBackButtonClick = { navController.navigateUp() },
            onHistoryClick = { viewModel.onEvent(WarikanEvent.HistoryClickEvent) }
        )
        Text(
            text = "割合を決めてね",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.surface
        )
        ProvideLayoutDirection(layoutDirection = LayoutDirection.Rtl) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Switch(
                        modifier = Modifier.rotate(180f),
                        checked = isSave.value,
                        onCheckedChange = { isSave.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MainAccent,
                            checkedTrackColor = SubAccent,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                    Text(
                        text = "保存",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.surface,
                    )
                }
                // 追加ボタン
                ShadowButton(
                    text = "追加",
                    padding = 80,
                    backGroundColor = MaterialTheme.colors.primary,
                    borderColor = MaterialTheme.colors.background,
                    textStyle = MaterialTheme.typography.body1,
                    offsetY = 9.dp,
                    offsetX = 0.dp,
                    onClick = { viewModel.onEvent(WarikanEvent.AddWarikanEvent) }
                )
            }
        }
        NameToColor(members = viewModel.members)
        ColumnTableText()
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WarikanAndColorsScrollView(
                modifier = Modifier.weight(6.0f),
                warikans = warikanState.value,
                viewModel = viewModel
            )
            ShadowButton(
                text = "スタート！",
                modifier = Modifier.weight(2.0f),
                padding = 80,
                backGroundColor = MaterialTheme.colors.primary,
                borderColor = MaterialTheme.colors.background,
                textStyle = MaterialTheme.typography.body1,
                offsetY = 9.dp,
                offsetX = 0.dp,
                onClick = {
                    viewModel.onEvent(WarikanEvent.StartEvent)
                }
            )
        }
    }
}

@Composable
fun PageBackAndHistoryBar(
    onPageBackButtonClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {}
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
                    onClick = { onPageBackButtonClick(); }
                ),
            contentDescription = "page back button"
        )
        ShadowButton(text = "りれき", onClick = { onHistoryClick() })
    }
}

@Composable
fun WarikanAndColorsScrollView(
    modifier: Modifier = Modifier,
    warikans: List<Warikan>,
    viewModel: WarikanViewModel
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(warikans) { index, warikan ->
            WarikanItem(
                warikan = warikan,
                onDeleteClick = { viewModel.onEvent(WarikanEvent.DeleteWarikanEvent(index)) },
                onWarikanValueChange = { value: String, num: Int ->
                    viewModel.onEvent(WarikanEvent.EditWarikanEvent(value, index, num))
                }
            )
        }
    }
}

@Composable
fun ColumnTableText() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(2.0f),
            text = "一番払う",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.surface
        )
        Box(Modifier.weight(7.0f), contentAlignment = Alignment.Center) {
            Text(
                text = "割り勘の割合",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface
            )
        }
        Spacer(Modifier.weight(1.5f))
    }
}

@Composable
fun NameToColor(
    members: List<Member>,
    size: Dp = 15.dp
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in members.indices) {
            Row(
                Modifier.weight(1.0f),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .height(size)
                        .width(size)
                        .background(
                            Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[members[i].color]
                        )
                )
                AutoResizeText(
                    text = members[i].name,
                    maxLines = 2,
                    fontSizeRange = (FontSizeRange(
                        min = (MaterialTheme.typography.button.fontSize.value - 4).sp,
                        max = MaterialTheme.typography.button.fontSize
                    )),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.surface
                )
            }
        }
        for (i in members.size until MAX_MEMBER_NUM) {
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Composable
fun NameToColorLazy(
    members: List<Member>,
    size: Dp = 20.dp
) {
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(members) { member ->
            Box(
                Modifier
                    .height(size)
                    .width(size)
                    .background(
                        Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[member.color]
                    )
            )
            Spacer(modifier = Modifier.padding(end = 5.dp))
            Text(
                text = member.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface,
            )
            Spacer(modifier = Modifier.padding(end = 5.dp))
        }
    }
}

@Composable
private fun ProvideLayoutDirection(
    layoutDirection: LayoutDirection,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection,
        content = content,
    )
}