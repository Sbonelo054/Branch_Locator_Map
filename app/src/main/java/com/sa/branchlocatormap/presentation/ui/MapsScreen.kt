package com.sa.branchlocatormap.presentation.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sa.branchlocatormap.R
import com.sa.branchlocatormap.presentation.navigation.Screen
import com.sa.branchlocatormap.presentation.viewModel.BranchSharedViewModel
import com.sa.branchlocatormap.presentation.viewModel.MapsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch


/**
 * MapsScreen is the main screen responsible for:
 * - Displaying Google Map with bank branch markers
 * - Handling location permissions and updates
 * - Showing search functionality for branches
 * - Displaying nearby branches in a bottom sheet
 * - Navigating to branch detail screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    /**
     * ViewModel responsible for map-related logic and data.
     */
    val viewModel: MapsViewModel = koinViewModel()

    /**
     * Shared ViewModel used for passing selected branch
     * between screens (e.g., map → detail screen).
     */
    val sharedViewModel: BranchSharedViewModel = koinViewModel()

    /**
     * List of branches filtered by search query.
     */
    val filteredBranches by viewModel.filteredBranches.collectAsStateWithLifecycle()

    /**
     * List of nearest branches based on user location.
     */
    val nearbyBanks by viewModel.nearbyBranches.collectAsStateWithLifecycle()

    /**
     * Current search query entered by the user.
     */
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    /**
     * Android context used for system services.
     */
    val context = LocalContext.current

    /**
     * Coroutine scope tied to composable lifecycle.
     */
    val scope = rememberCoroutineScope()

    /**
     * Tracks whether location permission has been granted.
     */
    var hasPermission by remember { mutableStateOf(false) }

    /**
     * Ensures camera focuses on user location only once.
     */
    var hasFocusedOnUser by remember { mutableStateOf(false) }

    /**
     * Camera state for controlling Google Map viewport.
     */
    val cameraPositionState = rememberCameraPositionState()

    /**
     * Launcher for requesting location permission at runtime.
     */
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    /**
     * Triggers location permission request on first composition.
     */
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    /**
     * Fused location provider for receiving location updates.
     */
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Location request configuration (update interval + accuracy).
     */
    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000
        ).setMinUpdateIntervalMillis(1500).build()
    }

    /**
     * Callback for receiving location updates from system.
     */
    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                val location = result.lastLocation ?: return
                val latLng = LatLng(location.latitude, location.longitude)

                /**
                 * Update ViewModel with current location.
                 */
                viewModel.updateLocation(latLng)

                /**
                 * Move camera to user location only once.
                 */
                if (!hasFocusedOnUser) {
                    hasFocusedOnUser = true

                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                        )
                    }
                }
            }
        }
    }

    /**
     * Handles starting and stopping location updates
     * based on permission state and composable lifecycle.
     */
    DisposableEffect(hasPermission) {

        if (hasPermission &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    /**
     * Bottom sheet state for nearby branches UI.
     */
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    /**
     * Controls visibility of bottom sheet.
     */
    var showSheet by remember { mutableStateOf(false) }

    /**
     * Ensures bottom sheet is shown only once initially.
     */
    var hasShownSheet by remember { mutableStateOf(false) }

    /**
     * Triggers bottom sheet when nearby branches are available.
     */
    LaunchedEffect(nearbyBanks) {
        if (nearbyBanks.isNotEmpty() && !hasShownSheet) {
            showSheet = true
            hasShownSheet = true
        }
    }

    /**
     * Root container for map + UI overlays.
     */
    Box(modifier = modifier.fillMaxSize()) {

        /**
         * Google Map displaying branch markers.
         */
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = hasPermission
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true
            )
        ) {

            /**
             * Custom marker icon for bank branches.
             */
            val markerIcon = remember {
                createMarkerIcon(context, R.drawable.ic_bank)
            }

            /**
             * Moves camera to first branch on initial load.
             */
            LaunchedEffect(filteredBranches.firstOrNull()) {
                filteredBranches.firstOrNull()?.let {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            14f
                        )
                    )
                }
            }

            /**
             * Render markers for all searched bank branches.
             */
            filteredBranches.forEach { branch ->
                Marker(
                    state = MarkerState(
                        position = LatLng(branch.latitude, branch.longitude)
                    ),
                    title = branch.name,
                    snippet = branch.address,
                    icon = markerIcon,
                    onClick = {
                        sharedViewModel.selectBranch(branch)
                        navController.navigate(Screen.BRANCH_DETAIL)
                        true
                    }
                )
            }
        }

        /**
         * Search bar displayed on top of map.
         */
        BranchSearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.TopCenter)
        )

        /**
         * Bottom sheet showing nearby branches.
         */
        if (showSheet && nearbyBanks.isNotEmpty()) {

            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.nearby_branches),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    /**
                     * List of nearby branch cards.
                     */
                    nearbyBanks.forEach { branch ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    sharedViewModel.selectBranch(branch)
                                    navController.navigate(Screen.BRANCH_DETAIL)
                                    showSheet = false
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column(modifier = Modifier.weight(1f)) {

                                    Text(
                                        text = branch.name,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Text(
                                        text = branch.address,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Converts a drawable resource into a Google Maps BitmapDescriptor
 * used for custom marker icons.
 */
fun createMarkerIcon(
    context: Context,
    @DrawableRes resId: Int
): BitmapDescriptor {

    val drawable = ContextCompat.getDrawable(context, resId)
        ?: return BitmapDescriptorFactory.defaultMarker()

    val bitmap = createBitmap(
        drawable.intrinsicWidth.coerceAtLeast(1),
        drawable.intrinsicHeight.coerceAtLeast(1)
    )

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

/**
 * Search bar used to filter bank branches by name.
 */
@Composable
fun BranchSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(Icons.Default.Search, contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                placeholder = { Text(stringResource(R.string.search_branches)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}