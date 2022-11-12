package com.tbdeveloper.warikanapp.feature_roulette.presentation.member_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tbdeveloper.warikanapp.DarkThemeValHolder
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.AutoResizeText
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.FontSizeRange


@Composable
fun MemberAndColorItemBar(
    modifier: Modifier = Modifier,
    members: List<Member>,
    onClick: () -> Unit
) {
    Row(
        Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(color = Color.Black, radius = 18.dp),
            onClick = { onClick() }
        )
    ) {
        val size = members.size
        for (i in 0 until size) {
            MemberAndColorItem(
                modifier = modifier.weight(1.0f),
                member = members[i]
            )
        }
        for (i in size until 4) {
            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Composable
fun MemberAndColorItem(
    modifier: Modifier = Modifier,
    member: Member
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[member.color])
        )
        AutoResizeText(
            text = member.name,
            maxLines = 1,
            fontSizeRange = (FontSizeRange(
                min = (MaterialTheme.typography.button.fontSize.value - 4).sp,
                max = MaterialTheme.typography.button.fontSize
            )),
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.surface
        )
    }
}