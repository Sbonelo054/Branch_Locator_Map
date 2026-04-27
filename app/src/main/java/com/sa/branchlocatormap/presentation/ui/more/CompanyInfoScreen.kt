package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sa.branchlocatormap.R

/**
 * Displays general information about the application and its purpose.
 *
 * This screen provides users with a brief overview of:
 * - What the app does
 * - The core goal behind the product
 *
 * It uses [BaseInfoScreen] to maintain a consistent layout across
 * informational screens, including a header with an icon and title.
 *
 * Content is presented inside an [InfoCardSection] to ensure
 * visual consistency and readability.
 *
 * Typical use cases:
 * - Helping users understand the value of the app
 * - Providing context about the product's mission
 *
 * The text content is sourced from string resources to support
 * localization and easy updates.
 *
 * UI Structure:
 * - Header (icon + title)
 * - Informational card with descriptive text
 */
@Composable
fun CompanyInfoScreen() {

    BaseInfoScreen(
        title = stringResource(R.string.company_info),
        icon = Icons.Default.Business
    ) {

        InfoCardSection {

            Text(
                stringResource(R.string.branch_locator_map_helps_users_find_bank_branches_quickly_and_efficiently),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(R.string.our_goal_is_to_simplify_access_to_financial_services_through_location_based_technology),
                color = Color.Gray
            )
        }
    }
}