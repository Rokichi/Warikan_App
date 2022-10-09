package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.components.SettingAndHistoryBar
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.ShadowButton
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.WarikanEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.WarikanViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WarikansScreen(
    navController: NavController,
    viewModel: WarikanViewModel = hiltViewModel()
) {
    // 画面外のフォーカスを検知
    val focusManager = LocalFocusManager.current
    val warikanState = viewModel.warikanState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WarikanViewModel.UiEvent.DeleteError -> {
                    Toast.makeText(context, "割り勘要素を2つ未満にすることはできません", Toast.LENGTH_SHORT).show()
                }
                is WarikanViewModel.UiEvent.InputError -> {
                    when (event.errorNum) {
                        0 -> {
                            Toast.makeText(context, "合計金額を正しく入力してください", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            Toast.makeText(context, "メンバー名が未入力です", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is WarikanViewModel.UiEvent.NextPage -> {
                    navController.navigate(Screen.WarikanScreen.route + "/${event.id}")
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
        SettingAndHistoryBar()
        Text(
            text = "割合を決めてね",
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
            onClick = { viewModel.onEvent(WarikanEvent.AddWarikanEvent) }
        )
        ColumnTableText()
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WarikanAndColorsScrollView(
                modifier = Modifier.weight(6.0f),
                warikans = warikanState.value,
                viewModel = viewModel)
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

                }
            )
        }
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
            WarikanItem(warikan = warikan, onDeleteClick = {  }, onValueChange = { _ -> })
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
        Box(Modifier.weight(5.0f), contentAlignment = Alignment.Center) {
            Text(
                text = "割り勘の割合",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface
            )
        }
        Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Text(
                text = "比率",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.surface
            )
        }
        Spacer(Modifier.weight(1.5f))
    }
}