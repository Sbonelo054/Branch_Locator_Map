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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ContactUsScreen() {

    BaseInfoScreen(
        title = "Contact Us",
        icon = Icons.Default.Email
    ) {

        InfoCardSection {

            ContactRow(Icons.Default.Email, "Email", "support@branchlocator.com")
            ContactRow(Icons.Default.Call, "Phone", "+27 XXX XXX XXX")
            ContactRow(Icons.Default.LocationOn, "Location", "Johannesburg, South Africa")
        }
    }
}

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