package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TermsScreen() {

    BaseInfoScreen(
        title = "Terms of Service",
        icon = Icons.Default.Description
    ) {

        InfoCardSection {

            Text("• Use the app responsibly.")
            Text("• Do not misuse location or branch data.")
            Text("• Services may change without notice.")
        }
    }
}