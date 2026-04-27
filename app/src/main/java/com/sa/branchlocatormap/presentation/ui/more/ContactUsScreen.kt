package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sa.branchlocatormap.R

/**
 * Displays contact information for users to reach support or the organization.
 *
 * This screen provides essential contact details including:
 * - Email address
 * - Phone number
 * - Physical location
 *
 * It uses [BaseInfoScreen] to maintain a consistent layout with a header
 * (icon + title), and [InfoCardSection] to group contact details in a
 * visually structured card.
 *
 * Each contact item is rendered using [ContactRow], which presents:
 * - A relevant icon
 * - A label (e.g., Email, Phone)
 * - The corresponding value
 *
 * Typical use cases:
 * - Allowing users to reach customer support
 * - Providing official communication channels
 *
 * All text content is sourced from string resources to support
 * localization and maintainability.
 *
 * UI Structure:
 * - Header (Email icon + "Contact Us" title)
 * - Card containing contact rows (email, phone, location)
 *
 */
@Composable
fun ContactUsScreen() {

    BaseInfoScreen(
        title = stringResource(R.string.contact_us),
        icon = Icons.Default.Email
    ) {

        InfoCardSection {

            ContactRow(Icons.Default.Email,
                stringResource(R.string.email),
                stringResource(R.string.clientcare_capitecbank_co_za)
            )
            ContactRow(Icons.Default.Call, stringResource(R.string.phone),
                stringResource(R.string._27_860_66_77_18)
            )
            ContactRow(Icons.Default.LocationOn,
                stringResource(R.string.location),
                stringResource(R.string.johannesburg_south_africa)
            )
        }
    }
}

/**
 * A reusable row component for displaying contact-related information.
 *
 * This composable presents a single piece of contact data in a structured format,
 * including:
 * - A leading icon representing the type of information (e.g., phone, email, location)
 * - A title label describing the data
 * - A value displaying the actual contact detail
 *
 * The layout is horizontally arranged and vertically centered, making it suitable
 * for use inside cards or sections such as:
 * - Contact Us screen
 * - Help & Support screen
 * - Company or branch details
 *
 * Example usage:
 *
 * ContactRow(
 *     icon = Icons.Default.Phone,
 *     title = "Phone",
 *     value = "+27 123 456 789"
 * )
 *
 * @param icon The icon representing the type of contact information.
 * @param title A short label describing the information (e.g., "Email", "Phone").
 * @param value The actual contact detail to display.
 */
@Composable
fun ContactRow(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(icon, null, tint = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, fontWeight = FontWeight.Medium)
        }
    }
}