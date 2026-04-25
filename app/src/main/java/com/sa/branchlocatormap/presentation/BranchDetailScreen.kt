package com.sa.branchlocatormap.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun BranchDetailScreen(navController: NavController) {
    val sharedViewModel: BranchSharedViewModel = koinViewModel()
    val favouritesViewModel: FavouritesViewModel = koinViewModel()
    val selectedBranch by sharedViewModel.selectedBranch.collectAsState()

    LaunchedEffect(Unit) {
        if (selectedBranch == null) {
            navController.popBackStack()
        }
    }

    val branch = selectedBranch?: return
    var isFavourite by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

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

                        StatusBadge(branch.isOpen)
                    }

                    IconButton(
                        onClick = {
                            isFavourite = !isFavourite
                            if (isFavourite) {
                                favouritesViewModel.addFavourite(branch)
                            } else {
                                favouritesViewModel.deleteFavourite(branch)
                            }
                        },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
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

        ActionButtons(branch)

        Spacer(modifier = Modifier.height(16.dp))

        InfoCard {
            Column {
                DetailRow(Icons.Default.LocationOn, "Address", branch.address)
                DetailRow(Icons.Default.Phone, "Phone", branch.phone)
                DetailRow(Icons.Default.AccessTime, "Hours", "${branch.openTime} - ${branch.closeTime}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoCard {
            OpeningHoursSection(branch)
        }

        Spacer(modifier = Modifier.height(16.dp))

        InfoCard {
            Column {
                Text(
                    "Services",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                branch?.services?.forEach {
                    ServiceChip(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

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

@Composable
fun OpeningHoursSection(branch: BankBranchDetail) {
    Column {
        Text("Opening Hours", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(12.dp))

        OpeningHourRow("Mon - Fri", "${branch.openTime} - ${branch.closeTime}", true)
        OpeningHourRow("Saturday", "09:00 - 13:00", false)
        OpeningHourRow("Sunday", "Closed", false)
    }
}

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

@Composable
fun ServiceChip(text: String) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(4.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

fun makeDirectCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:${phoneNumber.trim()}")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.startActivity(intent)
}

fun openGoogleMapsNavigation(context: Context, lat: Double, lng: Double) {

    // Primary: turn-by-turn navigation
    val gmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")

    val mapIntent = Intent(Intent.ACTION_VIEW, gmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {

        // Fallback 1: generic geo intent
        val geoUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")

        val fallbackIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(fallbackIntent)
        } catch (e2: Exception) {

            // Fallback 2: browser Google Maps
            val webUri = Uri.parse(
                "https://www.google.com/maps/dir/?api=1&destination=$lat,$lng"
            )

            context.startActivity(
                Intent(Intent.ACTION_VIEW, webUri)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBranchDetail() {
    MaterialTheme {
        BranchDetailScreen(navController = NavController(LocalContext.current))
    }
}
