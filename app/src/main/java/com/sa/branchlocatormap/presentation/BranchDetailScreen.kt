package com.sa.branchlocatormap.presentation

/**
 * This file defines the Branch Detail screen and all related UI components and helper functions.
 *
 * The screen is responsible for:
 * - Displaying detailed information about a selected bank branch
 * - Handling favourite/unfavourite actions
 * - Providing actions such as calling the branch or opening navigation in Google Maps
 * - Displaying services, opening hours, and branch metadata
 */

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.material.icons.filled.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.presentation.viewModel.BranchSharedViewModel
import com.sa.branchlocatormap.presentation.viewModel.FavouritesViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

/**
 * Main screen that displays full details of a selected bank branch.
 */
@Composable
fun BranchDetailScreen(navController: NavController) {

    // Shared ViewModel holding the currently selected branch
    val sharedViewModel: BranchSharedViewModel = koinViewModel()

    // ViewModel responsible for managing favourites in local storage
    val favouritesViewModel: FavouritesViewModel = koinViewModel()

    // Observing selected branch state
    val selectedBranch by sharedViewModel.selectedBranch.collectAsState()

    /**
     * If no branch is selected, return to previous screen.
     */
    LaunchedEffect(Unit) {
        if (selectedBranch == null) {
            navController.popBackStack()
        }
    }

    val branch = selectedBranch ?: return

    // Local UI state for favourite toggle
    var isFavourite by remember { mutableStateOf(branch.isFavourite) }

    // Used to periodically refresh time-based UI logic
    val currentTime = remember { mutableStateOf(java.util.Calendar.getInstance()) }

    /**
     * Updates current time every 60 seconds.
     */
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = java.util.Calendar.getInstance()
            delay(60_000)
        }
    }

    // Determines whether the branch is currently open
    val isOpenNow = isBranchOpen(branch.openTime, branch.closeTime)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        /**
         * Header section showing branch name, status, favourite button, and address.
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(20.dp)
                .statusBarsPadding()
        ) {

            Column {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = branch.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        /**
                         * Displays whether branch is open or closed.
                         */
                        StatusBadge(isOpenNow)
                    }

                    /**
                     * Favourite toggle button.
                     */
                    IconButton(
                        onClick = {
                            val newValue = !isFavourite
                            isFavourite = newValue

                            val updatedBranch = branch.copy(isFavourite = newValue)

                            if (newValue) {
                                favouritesViewModel.addFavourite(updatedBranch)
                            } else {
                                favouritesViewModel.deleteFavourite(updatedBranch)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavourite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = branch.address,
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                DistancePill(branch.distance, isLight = true)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Action buttons for directions and calling the branch.
         */
        ActionButtons(branch)

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Basic branch information section.
         */
        InfoCard {
            Column {
                DetailRow(Icons.Default.LocationOn, "Address", branch.address)
                DetailRow(Icons.Default.Phone, "Phone", branch.phone)
                DetailRow(
                    Icons.Default.AccessTime,
                    "Hours",
                    "${branch.openTime} - ${branch.closeTime}"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Opening hours section.
         */
        InfoCard {
            OpeningHoursSection(branch)
        }

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * Services offered by the branch.
         */
        InfoCard {

            Column {

                Text(
                    "Services",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                val services = branch.services

                if (services.isEmpty()) {

                    Text(
                        "No services available",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                } else {

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        services.chunked(2).forEach { rowItems ->

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                rowItems.forEach { service ->
                                    ModernServiceTile(
                                        text = service,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                if (rowItems.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Displays open/closed status badge.
 */
@Composable
fun StatusBadge(isOpen: Boolean) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = if (isOpen)
            Color(0xFF00C853).copy(alpha = 0.2f)
        else
            Color.Red.copy(alpha = 0.2f)
    ) {
        Text(
            text = if (isOpen) "Open Now" else "Closed",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

/**
 * Displays distance in a styled pill UI.
 */
@Composable
fun DistancePill(distance: String, isLight: Boolean = false) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = if (isLight)
            Color.White.copy(alpha = 0.2f)
        else
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ) {
        Text(
            text = distance,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (isLight) Color.White else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

/**
 * Reusable card container for information sections.
 */
@Composable
fun InfoCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = cardElevation(4.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

/**
 * Displays a single row of icon + title + value.
 */
@Composable
fun DetailRow(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
        }
    }
}

/**
 * Action buttons for calling and navigation.
 */
@Composable
fun ActionButtons(branchDetail: BankBranchDetail) {
    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Button(
            onClick = {
                openGoogleMapsNavigation(context, branchDetail.latitude, branchDetail.longitude)
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Directions, null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Directions")
        }

        val callPermissionLauncher = rememberLauncherForActivityResult(
            contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                makeDirectCall(context, branchDetail.phone)
            }
        }

        OutlinedButton(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    makeDirectCall(context, branchDetail.phone)
                } else {
                    callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Call, null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Call")
        }
    }
}

/**
 * Opening hours section UI.
 */
@Composable
fun OpeningHoursSection(branch: BankBranchDetail) {
    Column {
        Text(
            "Opening Hours",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        OpeningHourRow("Mon - Fri", "${branch.openTime} - ${branch.closeTime}", true)
        OpeningHourRow("Saturday", "09:00 - 13:00", false)
        OpeningHourRow("Sunday", "Closed", false)
    }
}

/**
 * A single row representing opening hours for a specific day.
 *
 * Displays:
 * - The day label (e.g., "Mon - Fri", "Saturday")
 * - The operating hours or status (e.g., "08:00 - 17:00", "Closed")
 *
 * The text style and color can be highlighted based on the [highlight] flag,
 * typically used to emphasize standard weekday operating hours.
 *
 * @param day The day or day range label.
 * @param time The operating hours or status text.
 * @param highlight If true, applies emphasis styling to indicate primary hours.
 */
@Composable
fun OpeningHourRow(day: String, time: String, highlight: Boolean) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(day, fontWeight = if (highlight) FontWeight.SemiBold else FontWeight.Normal)
        Text(
            time,
            color = if (highlight) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

/**
 * A modern UI card component used to display a single bank service (e.g., ATM, Loans, Forex).
 *
 * This composable renders:
 * - A leading icon based on the service type
 * - The service name
 * - A small subtitle indicating availability
 *
 * The icon is dynamically resolved using [getServiceIcon].
 *
 * @param text The name of the service to display (e.g., "ATM", "Loans").
 * @param modifier Optional [Modifier] for styling and layout adjustments.
 */
@Composable
fun ModernServiceTile(
    text: String,
    modifier: Modifier = Modifier
) {
    val icon = getServiceIcon(text)

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /**
             * Icon container with a styled background to visually separate the icon
             * from the rest of the content.
             */
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.shapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            /**
             * Textual information for the service.
             */
            Column {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Available service",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * Maps service names to icons.
 */
fun getServiceIcon(service: String): ImageVector {
    return when (service.lowercase()) {
        "atm" -> Icons.Default.LocalAtm
        "loans", "loan" -> Icons.Default.AttachMoney
        "card services", "credit cards" -> Icons.Default.CreditCard
        "forex", "currency exchange" -> Icons.Default.CurrencyExchange
        "parking" -> Icons.Default.LocalParking
        "wifi" -> Icons.Default.Wifi
        "drive through" -> Icons.Default.DriveEta
        "banking" -> Icons.Default.AccountBalance
        "insurance" -> Icons.Default.Shield
        "investment" -> Icons.Default.TrendingUp
        else -> Icons.Default.Savings
    }
}

/**
 * Checks whether branch is currently open based on time and weekday.
 */
fun isBranchOpen(openTime: String, closeTime: String): Boolean {
    return try {

        val calendar = java.util.Calendar.getInstance()
        val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)

        if (dayOfWeek == java.util.Calendar.SUNDAY) return false

        val format = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        val nowCal = java.util.Calendar.getInstance()

        val openDate = format.parse(openTime)
        val closeDate = format.parse(closeTime)

        if (openDate != null && closeDate != null) {

            val openCal = java.util.Calendar.getInstance()
            val closeCal = java.util.Calendar.getInstance()

            openCal.time = openDate
            closeCal.time = closeDate

            openCal.set(
                nowCal.get(java.util.Calendar.YEAR),
                nowCal.get(java.util.Calendar.MONTH),
                nowCal.get(java.util.Calendar.DAY_OF_MONTH)
            )

            closeCal.set(
                nowCal.get(java.util.Calendar.YEAR),
                nowCal.get(java.util.Calendar.MONTH),
                nowCal.get(java.util.Calendar.DAY_OF_MONTH)
            )

            if (closeCal.before(openCal)) {
                closeCal.add(java.util.Calendar.DAY_OF_MONTH, 1)
            }

            nowCal.after(openCal) && nowCal.before(closeCal)
        } else false

    } catch (e: Exception) {
        false
    }
}

/**
 * Initiates a direct phone call.
 */
fun makeDirectCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:${phoneNumber.trim()}")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

/**
 * Opens Google Maps navigation with fallback options.
 */
fun openGoogleMapsNavigation(context: Context, lat: Double, lng: Double) {
    val gmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")

    val mapIntent = Intent(Intent.ACTION_VIEW, gmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {

        val geoUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")

        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, geoUri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (e2: Exception) {

            val webUri =
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng")

            context.startActivity(
                Intent(Intent.ACTION_VIEW, webUri)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}

/**
 * Preview of BranchDetailScreen for UI testing in Android Studio.
 */
@Preview
@Composable
fun PreviewBranchDetail() {
    MaterialTheme {
        BranchDetailScreen(navController = NavController(LocalContext.current))
    }
}