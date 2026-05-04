package com.sa.branchlocatormap.presentation.ui.more

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sa.branchlocatormap.R
import com.sa.branchlocatormap.presentation.components.BaseInfoScaffoldScreen
import com.sa.branchlocatormap.presentation.ui.SectionTitle

/**
 * Displays help and support information for using the app.
 *
 * This screen provides users with guidance on:
 * - Finding bank branches
 * - Saving favourites
 * - Getting directions
 *
 * It also includes a support action allowing users to send feedback.
 *
 * The layout is built using [BaseInfoScaffoldScreen] and consists of:
 * - Informational help cards ([HelpCard])
 * - Action items ([HelpActionCard])
 *
 * @see sendEmail
 */
@Composable
fun HelpSupportScreen(navController: NavController) {

    BaseInfoScaffoldScreen(
        title = stringResource(R.string.help_support),
        onBackClick = { navController.popBackStack() }
    ) {

        Column(modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionTitle(stringResource(R.string.getting_started))

            HelpCard(
                title = stringResource(R.string.finding_a_branch),
                desc = stringResource(R.string.use_the_map_or_search_bar_to_locate_nearby_branches_quickly),
                icon = Icons.Default.Map
            )

            HelpCard(
                title = stringResource(R.string.saving_favourites),
                desc = stringResource(R.string.tap_the_heart_icon_on_any_branch_to_save_it),
                icon = Icons.Default.FavoriteBorder
            )

            HelpCard(
                title = stringResource(R.string.getting_directions),
                desc = stringResource(R.string.open_any_branch_and_tap_directions_to_navigate),
                icon = Icons.Default.Directions
            )

            SectionTitle(stringResource(R.string.still_need_help))
            val context = LocalContext.current
            HelpActionCard(
                title = "Send Feedback",
                subtitle = stringResource(R.string.help_us_improve_the_app),
                icon = Icons.Default.Feedback
            ) {
                sendEmail(context)
            }
        }
    }
}

/**
 * Opens the email app with a pre-filled recipient and subject.
 *
 * Uses a mailto intent to send a support email.
 *
 * @param context Used to launch the email intent.
 */
fun sendEmail(context: Context) {
    val uri = Uri.parse(
            "mailto:stellenbosch.main@capitecbank.co.za?subject=" +
                    "${Uri.encode("Support Request")}")

    val intent = Intent(Intent.ACTION_SENDTO, uri)

    context.startActivity(intent)
}

/**
 * A reusable clickable card used for actionable help and support items.
 *
 * This composable represents a single action entry in Help & Support screens,
 * combining both informational and navigational behavior.
 *
 * @param title The main action title displayed to the user.
 * @param subtitle A short description explaining the action.
 * @param icon The icon representing the action type.
 * @param onClick Callback triggered when the user taps the card.
 */
@Composable
fun HelpActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

/**
 * A reusable card component used to display help-related information or guidance.
 *
 * This composable presents a compact, visually structured card that includes:
 * - A leading icon inside a styled circular/rounded container
 * - A title representing the help topic
 * - A short descriptive subtitle providing additional context
 *
 * It is designed for use in Help & Support screens where users need
 * quick access to common assistance topics or explanations.
 *
 * Typical use cases:
 * - FAQ sections
 * - Help & Support overview screens
 * - Onboarding guidance items
 *
 * Visual structure:
 * - Icon (themed primary color inside a soft background)
 * - Title (primary text, medium weight)
 * - Description (secondary muted text)
 *
 * Example usage:
 * ```
 * HelpCard(
 *     title = "How to save a branch",
 *     desc = "Tap the heart icon on any branch",
 *     icon = Icons.Default.Favorite
 * )
 * ```
 *
 * @param title The main title describing the help topic.
 * @param desc A short supporting description explaining the topic.
 * @param icon The icon representing the help category or action.
 */
@Composable
fun HelpCard(title: String, desc: String, icon: ImageVector) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(title, fontWeight = FontWeight.Medium)
                Text(desc, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}