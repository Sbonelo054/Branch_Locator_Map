package com.sa.branchlocatormap.presentation.ui.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A reusable base layout for informational screens within the "More" section.
 *
 * This composable provides a consistent screen structure that includes:
 * - A top header with an icon and title
 * - Proper handling of status bar spacing
 * - A vertically spaced content area
 *
 * The header follows the app’s design system by displaying:
 * - A rounded icon container with themed background
 * - A prominent screen title aligned horizontally
 *
 * The [content] lambda is scoped to [ColumnScope], allowing flexible composition
 * of vertically arranged UI elements such as:
 * - Info cards
 * - Text sections
 * - Action items
 *
 * This component is ideal for screens like:
 * - Help & Support
 * - Contact Us
 * - Company Info
 * - Privacy Policy
 * - Terms of Service
 *
 * @param title The title displayed in the header section.
 * @param icon The icon shown alongside the title to visually represent the screen.
 * @param content Composable content displayed below the header, arranged vertically.
 */
@Composable
fun BaseInfoScreen(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(38.dp)
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

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}

/**
 * A reusable card container used to display grouped informational content.
 *
 * This composable wraps content inside a [Card] with consistent styling,
 * including:
 * - Full width layout
 * - Rounded corners using [MaterialTheme.shapes.large]
 * - Subtle elevation for visual separation
 * - Standard inner padding
 *
 * It is designed to keep UI consistent across screens such as:
 * - Help & Support
 * - About / Company Info
 * - Privacy / Terms
 * - Any informational sections
 *
 * The [content] lambda is scoped to [ColumnScope], allowing flexible
 * vertical layouts such as:
 * - Text blocks
 * - Rows with icons
 * - Lists of items
 */
@Composable
fun InfoCardSection(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}