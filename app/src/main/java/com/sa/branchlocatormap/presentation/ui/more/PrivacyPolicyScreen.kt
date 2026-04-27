package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PrivacyPolicyScreen() {

    BaseInfoScreen(
        title = "Privacy Policy",
        icon = Icons.Default.Security
    ) {

        InfoCardSection {

            Text("• We only use location to show nearby branches.")
            Text("• We do not store personal location history.")
            Text("• No personal data is sold or shared.")
        }
    }
}