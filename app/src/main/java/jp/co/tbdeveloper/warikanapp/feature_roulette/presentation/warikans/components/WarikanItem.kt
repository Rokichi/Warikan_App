package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.warikans.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Warikan
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.utlis.CustomTextField
import jp.co.tbdeveloper.warikanapp.ui.theme.WarikanAppTheme

const val MAX_LENGTH_NUM = 2

@Composable
fun WarikanItem(
    modifier: Modifier = Modifier,
    warikan: Warikan,
    proportion: String,
    height: Dp = 50.dp,
    width: Dp = 35.dp,
    onDeleteClick: () -> Unit,
    onProportionValueChange: (String) -> Unit,
    onWarikanValueChange: (String, Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(2.0f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 15.dp)
                    .width(height - 15.dp)
                    .background(
                        if (warikan.color != -1) Member.memberColors[warikan.color]
                        else Color.LightGray
                    )
            )
        }
        WarikanField(
            modifier = Modifier.weight(6.0f),
            height = height,
            width = width,
            ratios = warikan.ratios,
            onValueChange = { value: String, num: Int ->
                onWarikanValueChange(value, num)
            }
        )
        Box(
            modifier = Modifier
                .weight(2.0f)
                .padding(horizontal = 5.dp)
        ) {
            CustomTextField(
                maxLength = 2,
                fontSize = MaterialTheme.typography.h2.fontSize,
                offsetY = (-3).dp,
                placeholderText = "1",
                text = proportion,
                height = height,
                width = width,
                isOnlyNum = true,
                onValueChange = {
                    onProportionValueChange(it)
                }
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_close),
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
    height: Dp = 40.dp,
    width: Dp = 20.dp,
    ratios: List<String>,
    onValueChange: (String, Int) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTextField(
            maxLength = 2,
            fontSize = MaterialTheme.typography.h2.fontSize,
            offsetY = (-3).dp,
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WarikanAppTheme {
        WarikanItem(warikan = Warikan(
            ratios = "1:9".split(":"),
            proportion = 1,
            color = -1
        ),
            proportion = "",
            onDeleteClick = { /*TODO*/ },
            onWarikanValueChange = { _, _ -> },
            onProportionValueChange = { _ -> })
    }
}