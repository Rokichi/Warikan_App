package jp.co.tbdeveloper.warikanapp.feature_roulette.presentation.license.cpmponents

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun LicensePage() {
    AndroidView(
        factory = ::WebView,
        update = { webView ->
            webView.loadUrl("file:///android_asset/licenses.html")
        }
    )
}