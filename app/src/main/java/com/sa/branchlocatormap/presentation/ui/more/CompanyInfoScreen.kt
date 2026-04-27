package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CompanyInfoScreen() {

    BaseInfoScreen(
        title = "Company Info",
        icon = Icons.Default.Business
    ) {

        InfoCardSection {

            Text(
                "Branch Locator Map helps users find bank branches quickly and efficiently.",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Our goal is to simplify access to financial services through location-based technology.",
                color = Color.Gray
            )
        }
    }
}