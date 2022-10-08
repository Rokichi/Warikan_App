package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.Warikans.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.CustomTextField

@Composable
fun WarikanItem(
    modifier: Modifier = Modifier,
    warikan: Warikan,
    height: Dp = 40.dp,
    onDeleteClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(2.0f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 10.dp)
                    .width(height - 10.dp)
                    .background(
                        if (warikan.color != -1) Member.memberColors[warikan.color]
                        else Color.LightGray
                    )
            )
        }

        CustomTextField(
            modifier = Modifier.weight(2.0f),
            fontSize = MaterialTheme.typography.body1.fontSize,
            placeholderText = "5",
            text = "",
            height = height,
            onValueChange = {onValueChange(it)}
        )
        Text(
            modifier = Modifier.weight(2.0f),
            text = "合計金額",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )
        Spacer(modifier = Modifier.weight(1.0f))
        CustomTextField(
            modifier = Modifier.weight(2.0f),
            fontSize = MaterialTheme.typography.body1.fontSize,
            placeholderText = "5",
            text = "",
            height = height,
            onValueChange = {onValueChange(it)}
        )
        Spacer(modifier = Modifier.weight(2.0f))
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(2.0f)
                .height(height - 10.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, bounded = true),
                    onClick = { onDeleteClick() }
                ),
            contentDescription = "close image btn",
        )
    }
}