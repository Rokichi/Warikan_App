package com.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tbdeveloper.warikanapp.DarkThemeValHolder
import com.tbdeveloper.warikanapp.R
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import com.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import com.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.CustomTextField


@Composable
fun WarikanItem(
    modifier: Modifier = Modifier,
    members:List<Member>,
    warikan: Warikan,
    height: Dp = 50.dp,
    width: Dp = 35.dp,
    onDeleteClick: () -> Unit,
    onWarikanValueChange: (String, Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(2.0f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 15.dp)
                    .width(height - 15.dp)
                    .background(
                        if (warikan.color != -1) Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[warikan.color]
                        else Color.LightGray
                    )
            )
        }
        Box(
            Modifier.weight(7.0f),
            contentAlignment = Alignment.Center
        ) {
            WarikanField(
                height = height,
                width = width,
                members = members,
                ratios = warikan.ratios,
                onValueChange = { value: String, num: Int ->
                    onWarikanValueChange(value, num)
                }
            )
        }
        Image(
            painter = painterResource(
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_close_dark
                else R.drawable.ic_close_light
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1.5f)
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

@Composable
fun WarikanField(
    modifier: Modifier = Modifier,
    members:List<Member>,
    height: Dp = 40.dp,
    width: Dp = 20.dp,
    ratios: List<String>,
    onValueChange: (String, Int) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTextField(
            maxLength = 2,
            fontSize = MaterialTheme.typography.h2.fontSize,
            offsetY = (-3).dp,
            color = Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[members[0].color],
            placeholderText = "5",
            text = ratios[0],
            height = height,
            width = width,
            isOnlyNum = true,
            onValueChange = { onValueChange(it, 0) }
        )
        for (i in 1 until ratios.size) {
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = ":",
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.surface,
                textAlign = TextAlign.Center
            )

            CustomTextField(
                maxLength = 2,
                fontSize = MaterialTheme.typography.h2.fontSize,
                offsetY = (-3).dp,
                color = Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[members[i].color],
                placeholderText = "5",
                text = ratios[i],
                height = height,
                width = width,
                isOnlyNum = true,
                onValueChange = { onValueChange(it, i) }
            )
        }
    }
}