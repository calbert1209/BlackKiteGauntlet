package com.calbert.blackkitegauntlet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.calbert.blackkitegauntlet.presentation.theme.BlackKiteGauntletTheme
import com.calbert.blackkitegauntlet.presentation.ui.MainView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            BlackKiteGauntletTheme {
                MainView()
            }
        }
    }
}
