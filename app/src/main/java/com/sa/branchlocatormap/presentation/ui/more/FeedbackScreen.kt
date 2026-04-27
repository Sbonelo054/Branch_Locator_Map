package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackScreen() {

    BaseInfoScreen(
        title = "Feedback",
        icon = Icons.Default.Feedback
    ) {

        InfoCardSection {

            Text(
                text = "We’d love your thoughts on how to improve the app.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your feedback helps improve branch accuracy, usability, and features.",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}