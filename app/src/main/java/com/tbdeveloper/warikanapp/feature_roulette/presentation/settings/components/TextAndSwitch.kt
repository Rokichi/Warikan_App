package com.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import com.tbdeveloper.warikanapp.ui.theme.MainAccent
import com.tbdeveloper.warikanapp.ui.theme.SubAccent

@Composable
fun TextAndSwitch(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: State<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.surface
        )

        Switch(
            modifier = Modifier.rotate(180f),
            checked = isChecked.value,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MainAccent,
                checkedTrackColor = SubAccent,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}