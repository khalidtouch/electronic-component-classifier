package com.nkoyo.componentidentifier

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.nkoyo.componentidentifier.datastore.DarkThemeConfig
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import com.nkoyo.componentidentifier.ui.navigation.NavHostWrapper
import com.nkoyo.componentidentifier.ui.theme.ComponentIdentifierTheme
import com.nkoyo.componentidentifier.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel<MainViewModel>()
            val darkThemeConfigSettings by mainViewModel.darkThemeConfigSettings.collectAsStateWithLifecycle()
            val shouldUseDarkTheme = when (darkThemeConfigSettings.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                DarkThemeConfig.DARK -> true
                DarkThemeConfig.LIGHT -> false
            }

            ComponentIdentifierTheme(
                darkTheme = shouldUseDarkTheme
            ) {
                ComponentIdentifierTheme {
                    val navController = rememberNavController()
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHostWrapper(
                            navController = navController,
                            onAbortApplication = { finish() },
                            windowSizeClass = calculateWindowSizeClass(activity = this)
                        )
                    }
                }
            }
        } //end of onCreate
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        //stable
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}