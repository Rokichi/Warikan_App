package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import jp.co.tbdeveloper.warikanapp.ui.theme.MainAccent
import jp.co.tbdeveloper.warikanapp.ui.theme.SubAccent

@Composable
fun TextAndSwitch(
    text: String,
    isChecked: State<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
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