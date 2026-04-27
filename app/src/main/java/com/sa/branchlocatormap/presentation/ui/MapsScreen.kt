package com.sa.branchlocatormap.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.location.Location
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
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
@SuppressLint("DefaultLocale")
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
     * Current location of the user
     */
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()

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
     * [scaffoldState] keeps the state of the bottom sheet whether it's pulled up or pulled down
     */
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = stringResource(R.string.nearby_branches),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                /**
                 * A list of 3 nearby branches
                 */
                nearbyBanks.forEach { branch ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                sharedViewModel.selectBranch(branch)
                                navController.navigate(Screen.BRANCH_DETAIL)
                            },
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalance,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = branch.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Place,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = Color.Gray
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        Column{
                                            if(currentLocation != null) {
                                                val results = FloatArray(1)
                                                Location.distanceBetween(
                                                    currentLocation?.latitude?: return@Column,
                                                    currentLocation?.longitude?: return@Column,
                                                    branch.latitude,
                                                    branch.longitude,
                                                    results
                                                )
                                                Text(
                                                    text = String.format("%.2f Km", results[0] / 1000),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))
                                    val isOpenNow = isBranchOpen(branch.openTime, branch.closeTime)
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = if (isOpenNow)
                                            Color(0xFFE8F5E9)
                                        else
                                            Color(0xFFFFEBEE)
                                    ) {
                                        /**
                                         * Determines whether the branch is currently open
                                         */

                                        Text(
                                            text = if (isOpenNow) stringResource(R.string.open) else stringResource(R.string.closed),
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isOpenNow)
                                                Color(0xFF2E7D32)
                                            else
                                                Color(0xFFC62828)
                                        )
                                    }
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    ){
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

            var searchBarHeight by remember { mutableIntStateOf(0) }
            val density = LocalDensity.current
            val searchBarHeightDp = with(density) { searchBarHeight.toDp() }

            /**
             * Displays an "empty state" UI when:
             * - The user has entered a search query
             * - AND no matching bank branches are found
             *
             * This provides user feedback instead of showing a blank map,
             * improving usability and search clarity.
             */
            if (searchQuery.isNotBlank() && filteredBranches.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                        top = searchBarHeightDp + 24.dp,
                        start = 24.dp,
                        end = 24.dp
                    ),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .then(Modifier)
                    )

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.no_branches_found),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = stringResource(R.string.try_a_different_name_or_keyword),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )
                        }
                    }
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
                    .onGloballyPositioned { coordinates ->
                        searchBarHeight = coordinates.size.height
                    }
            )
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
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onQueryChange(query)
                    }
                ),
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