package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.memberhistory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.resource.Member


@Composable
fun MemberAndColorItemBar(
    modifier: Modifier = Modifier,
    members: List<Member>,
    onClick: () -> Unit
) {
    Row(
        Modifier.clickable { onClick() }
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
            modifier = modifier.background(Member.memberColors(isSystemInDarkTheme())[member.color])
        )
        Text(
            text = member.name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )
    }
}