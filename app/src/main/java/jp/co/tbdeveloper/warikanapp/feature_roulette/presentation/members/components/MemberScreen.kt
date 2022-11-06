package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.CustomTextField
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.ShadowButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MembersScreen(
    navController: NavController,
    viewModel: MemberViewModel = hiltViewModel(),
) {
    // 画面外のフォーカスを検知
    val focusManager = LocalFocusManager.current

    val totalState = viewModel.totalState.value
    val memberState = viewModel.memberState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MemberViewModel.UiEvent.DeleteError -> {
                    Toast.makeText(context, "メンバーを2人未満にすることはできません", Toast.LENGTH_SHORT).show()
                }
                is MemberViewModel.UiEvent.InputError -> {
                    when (event.errorNum) {
                        0 -> {
                            Toast.makeText(context, "合計金額を正しく入力してください", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            Toast.makeText(context, "メンバー名が未入力です", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is MemberViewModel.UiEvent.NextPage -> {
                    navController.navigate(Screen.WarikanScreen.route + "/${event.members}/${event.total}")
                }
                is MemberViewModel.UiEvent.SettingPage -> {
                    navController.navigate(Screen.SettingsScreen.route)
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
        SettingAndHistoryBar(
            onSettingClick = { navController.navigate(Screen.SettingsScreen.route) },
            onHistoryClick = { }
        )
        Text(
            text = "メンバーを決めてね",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.surface
        )
        // 追加ボタン
        ShadowButton(
            text = "追加",
            padding = 80,
            backGroundColor = MaterialTheme.colors.primary,
            borderColor = MaterialTheme.colors.background,
            textStyle = MaterialTheme.typography.body1,
            offsetY = 9.dp,
            offsetX = 0.dp,
            onClick = { viewModel.onEvent(MemberEvent.AddMemberEvent) }
        )
        Spacer(Modifier.height(5.dp))
        // お金入力フィールド
        InputSumOfAccount(totalState, viewModel)
        // メンバ追加用
        ColumnTableText()
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemberAndColorsScrollView(
                modifier = Modifier.weight(6.0f),
                members = memberState.value,
                viewModel = viewModel
            )
            ShadowButton(
                text = "次へ！",
                modifier = Modifier.weight(2.0f),
                padding = 80,
                backGroundColor = MaterialTheme.colors.primary,
                borderColor = MaterialTheme.colors.background,
                textStyle = MaterialTheme.typography.body1,
                offsetY = 9.dp,
                offsetX = 0.dp,
                onClick = {
                    viewModel.onEvent(MemberEvent.NextPageEvent)
                }
            )
        }
    }

}

@Composable
fun SettingAndHistoryBar(
    onSettingClick: () -> Unit = {},
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
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_settings_dark
                else R.drawable.ic_settings_light
            ),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, radius = 19.dp),
                    onClick = { onSettingClick() }
                ),
            contentDescription = "setting image btn"
        )
        ShadowButton(text = "りれき", onClick = { onHistoryClick() })
    }
}

@Composable
fun InputSumOfAccount(
    text: String,
    viewModel: MemberViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp)
    ) {
        Text(
            text = "合計金額",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )
        CustomTextField(
            fontSize = MaterialTheme.typography.h2.fontSize,
            placeholderText = "2000",
            width = 100.dp,
            height = 50.dp,
            isOnlyNum = true,
            text = text,
            onValueChange = {
                viewModel.onEvent(MemberEvent.EditTotalEvent(it))
            }
        )
        Text(
            text = "円",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )
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
        Box(Modifier.weight(6.0f), contentAlignment = Alignment.Center) {
            Text(
                text = "メンバー",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface
            )
        }
        Box(Modifier.weight(1.5f), contentAlignment = Alignment.Center) {
            Text(
                text = "色",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface
            )
        }
        Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Spacer(Modifier.height(1.dp))
        }
    }
}

@Composable
fun MemberAndColorsScrollView(
    modifier: Modifier = Modifier,
    members: List<Member>,
    viewModel: MemberViewModel
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(members) { index, member ->
            MemberItem(
                Modifier,
                member = member,
                onDeleteClick = { viewModel.onEvent(MemberEvent.DeleteMemberEvent(index)) },
                onValueChange = { viewModel.onEvent(MemberEvent.EditMemberEvent(it, index)) })
        }
    }
}