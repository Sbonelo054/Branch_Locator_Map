package com.sa.branchlocatormap.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.sa.branchlocatormap.presentation.theme.BranchLocatorMapTheme

/**
 * Entry point of the application.
 *
 * This Activity hosts the Jetpack Compose UI and serves as the single
 * Android entry screen for the app.
 *
 * Responsibilities:
 * - Sets up edge-to-edge UI rendering
 * - Applies app theme
 * - Loads the root composable (MainScreen)
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the Activity is first created.
     *
     * This is where the Compose UI tree is initialized.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BranchLocatorMapTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BranchLocatorMapTheme {
        MapsScreen(
            modifier = Modifier,
            navController = NavController(LocalContext.current)
        )
    }
}