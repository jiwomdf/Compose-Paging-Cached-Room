package com.katilijiwoadiwiyono.imagegallerycompose.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.darkModeState
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.DashboardScreen
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.ImageGalleryComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ImageGalleryComposeTheme(
                isUseDarkTheme = darkModeState.value
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DashboardScreen(mainViewModel)
                }
            }
        }
    }
}