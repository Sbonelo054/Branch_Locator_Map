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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sa.branchlocatormap.R

/**
* Displays a feedback screen where users are encouraged to share their thoughts
* and suggestions about the application.
*
* This screen provides a simple informational layout that:
* - Encourages users to provide feedback
* - Explains the purpose and value of their input
*
* It uses [BaseInfoScreen] to maintain a consistent header layout (icon + title),
* and [InfoCardSection] to present feedback-related messaging in a structured card.
*
* Unlike other contact-related screens, this screen is intentionally simple and
* does not include input fields, focusing instead on guiding users toward feedback actions
* (e.g., navigating to an external feedback form or email intent in a future enhancement).
*/
@Composable
fun FeedbackScreen() {

    BaseInfoScreen(
        title = stringResource(R.string.feedback),
        icon = Icons.Default.Feedback
    ) {

        InfoCardSection {

            Text(
                text = stringResource(R.string.we_d_love_your_thoughts_on_how_to_improve_the_app),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.your_feedback_helps_improve_branch_accuracy_usability_and_features),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}