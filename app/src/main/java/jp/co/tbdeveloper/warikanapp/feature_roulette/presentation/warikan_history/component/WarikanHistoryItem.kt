package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MAX_MEMBER_NUM
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.AutoResizeText
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.FontSizeRange

@Composable
fun WarikanHistoryItem(
    memberSize: Int,
    warikans: List<Warikan>,
    onClick: () -> Unit
) {
    val divSize = if(memberSize.toInt() != 4) 4 else 3
    if (warikans.size > divSize) {
        Column(
            modifier = Modifier.clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            val warikansList = listOf(
                warikans.slice(0 until 3),
                warikans.slice(3 until warikans.lastIndex)
            )
            for (warikanList in warikansList) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for ((index, warikan) in warikanList.withIndex()) {
                        val endComma = if (index != warikans.lastIndex) "," else ""
                        Text(
                            modifier = Modifier.weight(1.0f),
                            text = warikan.ratios.joinToString(":") + endComma,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.surface
                        )
                    }
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.clickable { onClick() },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for ((index, warikan) in warikans.withIndex()) {
                val endComma = if (index != warikans.lastIndex) "," else ""
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = warikan.ratios.joinToString(":") + endComma,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.surface
                )
            }
        }
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

/*
* Box(
                Modifier
                    .height(height - 15.dp)
                    .width(height - 15.dp)
                    .background(
                        if (warikan.color != -1) Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[warikan.color]
                        else Color.LightGray
                    )
            )
* */