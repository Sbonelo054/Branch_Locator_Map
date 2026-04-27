package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sa.branchlocatormap.R

/**
 * Displays the app's terms of service.
 *
 * Outlines basic rules for using the application responsibly.
 */
@Composable
fun TermsScreen() {
    BaseInfoScreen(
        title = stringResource(R.string.terms_of_service),
        icon = Icons.Default.Description
    ) {

        InfoCardSection {
            Text(stringResource(R.string.use_the_app_responsibly))
            Text(stringResource(R.string.do_not_misuse_location_or_branch_data))
            Text(stringResource(R.string.services_may_change_without_notice))
        }
    }
}