package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.members.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.R
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.Member
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.roulettes.utlis.CustomTextField

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    member: Member,
    height: Dp = 40.dp,
    onDeleteClick: () -> Unit,
    onValueChange: () -> Unit

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.weight(6.0f), contentAlignment = Alignment.Center) {
            CustomTextField(
                fontSize = MaterialTheme.typography.body1.fontSize,
                placeholderText = "name",
                text = member.name,
                height = height,
//                onValueChange = { _, _ ->  }
                onValueChange = { onValueChange() }
            )
        }
        Box(Modifier.weight(1.5f), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .height(height - 10.dp)
                    .width(height - 10.dp)
                    .background(Member.memberColors[member.color])
            )
        }
        Box(Modifier.weight(1.0f), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(height - 10.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(color = Color.Black, bounded = true),
                        onClick = { onDeleteClick() }
                    ),
                contentDescription = "setting image btn",
            )
        }
    }
}