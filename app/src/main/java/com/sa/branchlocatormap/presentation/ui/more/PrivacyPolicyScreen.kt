package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sa.branchlocatormap.R

/**
 * Displays the app's privacy policy information.
 *
 * Shows how user data (location) is used within the app.
 */
@Composable
fun PrivacyPolicyScreen() {

    BaseInfoScreen(
        title = stringResource(R.string.privacy_policy),
        icon = Icons.Default.Security
    ) {
        InfoCardSection {
            Text(stringResource(R.string.we_only_use_location_to_show_nearby_branches))
            Text(stringResource(R.string.we_do_not_store_personal_location_history))
            Text(stringResource(R.string.no_personal_data_is_sold_or_shared))
        }
    }
}