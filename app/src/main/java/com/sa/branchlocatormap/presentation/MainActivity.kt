package com.sa.branchlocatormap.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.MapsInitializer
import com.sa.branchlocatormap.ui.theme.BranchLocatorMapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MapsInitializer.initialize(this)
        setContent {
            BranchLocatorMapTheme {
                var showSplash by remember { mutableStateOf(true) }

                    if (showSplash) {
                        SplashScreen(
                            onNavigateToMain = {
                                showSplash = false
                            }
                        )
                    } else {

                        MainScreen()
                    }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BranchLocatorMapTheme {
        MapScreen()
    }
}