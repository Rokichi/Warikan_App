package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.WarikanHistoryEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.WarikanHistoryViewModel
import jp.co.tbdeveloper.warikanapp.ui.theme.DarkTextGray
import jp.co.tbdeveloper.warikanapp.ui.theme.LightTextGray
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WarikanHistoryScreen(
    navController: NavController,
    viewModel: WarikanHistoryViewModel = hiltViewModel(),
    popBackStack: (Array<Warikan>) -> Unit
) {
    val warikansList = viewModel.warikanHistoriesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WarikanHistoryViewModel.UiEvent.ItemSelected -> {
                    popBackStack(event.warikans)
                }
            }
        }
    }

    Column(
        // 一回り小さく配置
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PageBackBar { navController.navigateUp() }
        if (warikansList.value.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(warikansList.value) { index: Int, warikans: List<Warikan> ->
                    //    viewModel.onEvent(WarikanHistoryEvent.OnItemClick(index))
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(color = Color.Black, radius = 18.dp),
                            onClick = { viewModel.onEvent(WarikanHistoryEvent.OnItemClick(index)) }
                        ),
                        text = "${warikans}"
                    )
                    if (index < warikansList.value.lastIndex) {
                        Divider(
                            color = if (DarkThemeValHolder.isDarkTheme.value) DarkTextGray
                            else LightTextGray
                        )
                    }
                }
            }
        } else {
            Text(
                text = "メンバー数:${viewModel.size}の履歴はありません",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.surface,
            )
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
        horizontalArrangement = Arrangement.Start,
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
    }
}