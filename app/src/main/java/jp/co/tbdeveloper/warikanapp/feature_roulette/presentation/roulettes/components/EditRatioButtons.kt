package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.RouletteViewModel
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.RoulettesEvent

@Composable
fun EditRatioButtons(
    viewModel: RouletteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    warikans: List<Warikan>,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, warikan) in warikans.withIndex()) {
            EditRatioButton(
                columnModifier = Modifier.weight(1.0f),
                modifier = Modifier.size(30.dp),
                warikan = warikan
            ) {
                viewModel.onEvent(RoulettesEvent.EditRatioButtonClick(index, it))
            }
        }
    }
}


/**
 *
 *
 * @param modifier must has size
 * @param warikan warikan
 * @param onClick true -> up, false -> down
 */
@Composable
fun EditRatioButton(
    columnModifier: Modifier,
    modifier: Modifier,
    warikan: Warikan,
    onClick: (Boolean) -> Unit
) {
    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_keyboard_arrow_up_dark
                else R.drawable.ic_keyboard_arrow_up_light
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(2.0f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, bounded = true),
                    onClick = { onClick(true) }
                ),
            contentDescription = "ratio up btn",
        )
        Box(
            modifier = modifier
                .weight(3.0f)
                .fillMaxSize()
                .background(
                    if (warikan.color != -1) Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[warikan.color]
                    else Color.LightGray,
                )
        )
        Image(
            painter = painterResource(
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_keyboard_arrow_down_dark
                else R.drawable.ic_keyboard_arrow_down_light
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(2.0f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, bounded = true),
                    onClick = { onClick(false) }
                ),
            contentDescription = "ratio down btn",
        )
    }
}