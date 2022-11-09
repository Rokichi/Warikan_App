package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikan_history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan

@Composable
fun WarikanHistoryItem(
    memberSize: Int,
    members: List<Member>,
    warikans: List<Warikan>,
    onClick: () -> Unit
) {
    val divSize = if (memberSize != 4) 5 else 4
    val memberColor = members.map {
        Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[it.color]
    }

    if (warikans.size >= divSize) {
        Column(
            modifier = Modifier.clickable { onClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            val warikansList = listOf(
                warikans.slice(0..divSize - 2),
                warikans.slice(divSize - 1..warikans.lastIndex)
            )
            for (warikanList in warikansList) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // きれいに表示
                    for (i in warikanList.indices) {
                        ColorAndRatio(
                            modifier = Modifier.weight(1.0f),
                            warikan = warikanList[i],
                            memberColor = memberColor
                        )
                    }
                    for (i in warikanList.lastIndex until divSize - 2) {
                        Spacer(Modifier.weight(1.0f))
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
            // きれいに表示
            for (i in warikans.indices) {
                ColorAndRatio(
                    modifier = Modifier.weight(1.0f),
                    warikan = warikans[i],
                    memberColor = memberColor
                )
            }
            for (i in warikans.lastIndex until divSize - 2) {
                Spacer(Modifier.weight(1.0f))
            }
        }
    }
}

@Composable
fun ColorAndRatio(
    modifier: Modifier = Modifier,
    size: Dp = 15.dp,
    warikan: Warikan,
    memberColor: List<Color>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(size)
                .background(
                    if (warikan.color != -1) Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[warikan.color]
                    else Color.LightGray
                )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            for ((index, ratio) in warikan.ratios.withIndex()) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .border(
                            1.dp,
                            memberColor[index],
                            RoundedCornerShape(5.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier
                            .offset(y = (-8).dp)
                            .padding(horizontal = 4.dp),
                        text = ratio,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.surface
                    )
                }
                if (index != warikan.ratios.lastIndex) {
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.surface
                    )
                }
            }
        }
    }

}
