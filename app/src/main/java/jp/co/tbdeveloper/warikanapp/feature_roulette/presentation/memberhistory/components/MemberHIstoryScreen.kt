package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory.MemberHistoryEvent
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory.MemberHistoryViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.Screen
import jp.co.tbdeveloper.warikanapp.ui.theme.DarkTextGray
import jp.co.tbdeveloper.warikanapp.ui.theme.LightTextGray
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MemberHistoryScreen(
    navController: NavController,
    viewModel: MemberHistoryViewModel = hiltViewModel()
) {
    val membersList = viewModel.memberHistoriesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MemberHistoryViewModel.UiEvent.ItemSelected -> {
                    navController.navigate(Screen.MemberScreen.route + "/${event.members}") {
                        popUpTo(0)
                    }
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
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            itemsIndexed(membersList.value) { index: Int, members: List<Member> ->
                MemberAndColorItemBar(members = members) {
                    viewModel.onEvent(MemberHistoryEvent.OnItemClick(index))
                }
                if (index < membersList.value.lastIndex) {
                    Divider(
                        color = if (DarkThemeValHolder.isDarkTheme.value) DarkTextGray
                        else LightTextGray
                    )
                }
            }
        }
    }
}
