package com.opencode.android

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OpenCodeUI()
                }
            }
        }
    }
}

@Composable
fun OpenCodeUI() {
    var state by remember { mutableStateOf("starting") }
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        state = "starting"
        try {
            val bin = ctx.assets.open("opencode-aarch64")
            val dest = ctx.filesDir.resolve("opencode-aarch64")
            dest.outputStream().use { out -> bin.copyTo(out) }
            dest.setExecutable(true)

            val pb = ProcessBuilder(
                dest.absolutePath,
                "serve",
                "--hostname", "127.0.0.1",
                "--port", "4096"
            )
            pb.environment()["HOME"] = ctx.filesDir.absolutePath
            pb.environment()["OPENCODE_SERVER_PASSWORD"] = "opencode"
            pb.directory(ctx.filesDir)
            pb.redirectErrorStream(true)
            pb.start()

            delay(3000)
            state = "ready"
        } catch (e: Exception) {
            state = "error"
        }
    }

    when (state) {
        "starting" -> Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text("Starting OpenCode...", modifier = Modifier.padding(top = 16.dp))
            }
        }

        "ready" -> AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.allowFileAccess = false
                    settings.setSupportZoom(true)
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView, request: WebResourceRequest
                        ): Boolean = false
                    }
                    loadUrl("http://127.0.0.1:4096")
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        "error" -> Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Failed to start OpenCode server")
                Button(onClick = { state = "starting" }, modifier = Modifier.padding(top = 16.dp)) {
                    Text("Retry")
                }
            }
        }
    }
}
