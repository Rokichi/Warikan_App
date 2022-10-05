package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.Warikans.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.co.tbdeveloper.warikanapp.feature_roulette.domain.model.MemberEntity
import jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.members.MemberViewModel

@Composable
fun WarikansScreen(
    viewModel: MemberViewModel,
    id: Long
) {
    Log.i("id", id.toString())
    val state = remember {
        mutableStateOf(listOf<MemberEntity>(
            MemberEntity(0, 0, "aaa", 0),
            MemberEntity(0, 0, "bbb", 0),
            MemberEntity(0, 0, "ccc", 0),
        ))
    }

    LaunchedEffect(Unit) {
        viewModel.getMembers(id)!!.collect { members ->
            state.value = members
            Log.i("members", "$members")
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(state.value) { member ->
            Text(
                text = member.name, style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.surface
            )
        }
    }
}