package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.CustomTextField

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    member: Member,
    height: Dp = 50.dp,
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
        CustomTextField(
            modifier = Modifier.weight(6.0f),
            fontSize = MaterialTheme.typography.h2.fontSize,
            placeholderText = "name",
            text = member.name,
            height = height,
            offsetY = (-3).dp,
            onValueChange = { onValueChange(it) }
        )
        Box(Modifier.weight(1.5f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 15.dp)
                    .width(height - 15.dp)
                    .background(Member.memberColors(isSystemInDarkTheme())[member.color])
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1.0f)
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