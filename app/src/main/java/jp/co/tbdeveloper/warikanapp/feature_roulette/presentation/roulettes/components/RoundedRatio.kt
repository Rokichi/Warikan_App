package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.ui.theme.LightTextBlack

@Composable
fun RoundedRatio(
    memberColors: List<Color>,
    ratios: List<String>,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .border(
                    width = 1.dp,
                    color = memberColors[0],
                    shape = CircleShape
                )
                .padding(horizontal = 10.dp)
                .height(25.dp),
            //contentAlignment = Alignment.Center
        ) {
            Text(
                text = ratios[0],
                modifier = Modifier
                    .offset(y = (-5).dp),
                style = MaterialTheme.typography.body2,
                color = LightTextBlack
            )
        }
        for (i in 1 until ratios.size) {
            Text(":",
                style = MaterialTheme.typography.body2,
                color = LightTextBlack
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .border(
                        width = 1.dp,
                        color = memberColors[i],
                        shape = CircleShape
                    )
                    .padding(horizontal = 10.dp)
                    .height(25.dp),
            ) {
                Text(
                    text = ratios[i],
                    modifier = Modifier
                        .offset(y = (-5).dp),
                    style = MaterialTheme.typography.body2,
                    color = LightTextBlack
                )
            }
        }
    }
}