package com.tbdeveloper.warikanapp.feature_roulette.presentation.license.components

import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.tbdeveloper.warikanapp.DarkThemeValHolder

@Composable
fun LicencePage(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageBackBar { navController.navigateUp() }
        AndroidView(
            factory = ::WebView,
            update = { webView ->
                webView.loadUrl("file:///android_asset/licenses.html")
            }
        )
    }
}

@Composable
fun PageBackBar(
    onPageBackButtonClick: () -> Unit = {},
) {
    Row(
        // 横幅Max, 横は等間隔，縦は真ん中に
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(35.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (DarkThemeValHolder.isDarkTheme.value) R.drawable.ic_arrow_left_dark
                else R.drawable.ic_arrow_left_light
            ),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(color = Color.Black, radius = 18.dp),
                    onClick = { onPageBackButtonClick(); }
                ),
            contentDescription = "page back button"
        )
    }
}