package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components

import androidx.compose.foundation.background
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
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.ResultWarikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.AutoResizeText
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.FontSizeRange

@Composable
fun MemberAndColor(
    result: ResultWarikan,
    height: Dp = 50.dp
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 10.dp)
                    .width(height - 10.dp)
                    .background(Member.memberColors(DarkThemeValHolder.isDarkTheme.value)[result.color])
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))
        Box(Modifier.weight(6.0f), contentAlignment = Alignment.Center) {
            Row {
                Text(
                    modifier = Modifier.weight(5.0f),
                    text = "${result.name} さん",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.surface
                )
                Text(
                    modifier = Modifier.weight(2.0f),
                    text = "${result.proportion} 割",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.surface
                )
                AutoResizeText(
                    modifier = Modifier.weight(2.0f),
                    text = "${result.payment} 円",
                    maxLines = 2,
                    fontSizeRange = (FontSizeRange(
                        min = (MaterialTheme.typography.body1.fontSize.value - 4).sp,
                        max = MaterialTheme.typography.body1.fontSize
                    )),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.surface
                )
            }
        }
    }
}