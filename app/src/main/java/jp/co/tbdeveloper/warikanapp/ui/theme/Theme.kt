package jp.co.tbdeveloper.warikanapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import jp.co.tbdeveloper.warikanapp.DarkThemeValHolder

private val DarkColorPalette = darkColors(
    primary = DarkMainAccent,
    primaryVariant = DarkMainSubAccent,
    secondary = DarkSecondAccent,
    background = DarkBackGround,
    surface = DarkTextWhite,
)

private val LightColorPalette = lightColors(
    primary = LightMainAccent,
    primaryVariant = LightMainSubAccent,
    secondary = LightSecondAccent,
    background = LightBackGround,
    surface = LightTextBlack,
    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WarikanAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val isDarkThemeSelect = remember {
        DarkThemeValHolder.isDarkThemeSelect
    }
    val colors = when (isDarkThemeSelect.value) {
        0 -> {
            LightColorPalette
        }
        1 -> {
            DarkColorPalette
        }
        2 -> {
            if(darkTheme) DarkColorPalette else LightColorPalette
        }
        else -> {
            LightColorPalette
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}