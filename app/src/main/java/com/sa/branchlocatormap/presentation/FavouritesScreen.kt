package com.sa.branchlocatormap.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sa.branchlocatormap.domain.BankBranchDetail
import com.sa.branchlocatormap.presentation.viewModel.FavouritesViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Favourites screen that displays all saved bank branches.
 *
 * This screen observes favourite branches from [FavouritesViewModel] and
 * renders either:
 * - An empty state UI when no favourites exist
 * - A list of saved branches when data is available
 *
 * @param onBranchClick Callback triggered when a branch item is selected.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(onBranchClick: (BankBranchDetail) -> Unit) {
    val favouritesViewModel: FavouritesViewModel = koinViewModel()
    val favouritesState by favouritesViewModel.favouriteUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Favourites",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (favouritesState.favourites.isEmpty()) {
            EmptyFavouritesScreen()
        } else {
            BankBranchScreen(onBranchClick, favouritesState.favourites)
        }
    }
}

/**
 * Empty state UI shown when no favourite branches have been saved.
 *
 * Displays:
 * - Animated favorite icon
 * - Informational text explaining how to add favourites
 * - Simple usage hint
 */
@Composable
fun EmptyFavouritesScreen() {

    val infiniteTransition = rememberInfiniteTransition(label = "favAnim")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(110.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(44.dp)
                    .scale(scale)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "No saved branches yet",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Keep your preferred branches in one place for quick access anytime.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How it works",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Tap heart on a branch",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * LazyColumn wrapper that displays a list of bank branches.
 *
 * @param onBranchClick Callback when a branch is clicked.
 * @param branches List of branches to display.
 */
@Composable
fun BankBranchList(
    onBranchClick: (BankBranchDetail) -> Unit,
    branches: List<BankBranchDetail>
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(branches) { branch ->
            BankBranchItem(onBranchClick = onBranchClick, branch = branch)
        }
    }
}

/**
 * Individual bank branch item displayed in the favourites list.
 *
 * Shows:
 * - Branch name
 * - Open/closed status
 * - Address
 * - Distance
 * - Navigation CTA
 *
 * @param onBranchClick Callback when the item is clicked.
 * @param branch Branch data to render.
 */
@Composable
fun BankBranchItem(
    onBranchClick: (BankBranchDetail) -> Unit,
    branch: BankBranchDetail
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBranchClick(branch)
            },
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = branch.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (branch.isOpen) "Open" else "Closed",
                    color = if (branch.isOpen)
                        Color(0xFF2E7D32)
                    else
                        MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = branch.address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = branch.distance,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "View Details →",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Wrapper composable for rendering bank branch screen content.
 */
@Composable
fun BankBranchScreen(
    onBranchClick: (BankBranchDetail) -> Unit,
    branches: List<BankBranchDetail>
) {
    BankBranchList(onBranchClick = onBranchClick, branches = branches)
}

@Preview(showBackground = true)
@Composable
fun BankBranchScreenPreview() {
    val branches = listOf(
        BankBranchDetail("Standard Bank - Sandton", "123 Rivonia Rd", "1.2 km", true),
        BankBranchDetail("FNB - Rosebank", "45 Oxford Rd", "2.5 km", false),
        BankBranchDetail("Absa - Melrose Arch", "10 High St", "3.1 km", true),
        BankBranchDetail("Nedbank - Hyde Park", "88 Jan Smuts Ave", "4.0 km", true)
    )

    MaterialTheme {
        BankBranchList(onBranchClick = {}, branches = branches)
    }
}
