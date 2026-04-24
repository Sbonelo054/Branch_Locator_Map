package com.sa.branchlocatormap.presentation.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sa.branchlocatormap.R
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap
import com.sa.branchlocatormap.domain.BankBranchDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val sandton = LatLng(-26.1076, 28.0567)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sandton, 10f)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "markerPulse")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val animatedIcon = remember(scale) {
        createMarkerIcon(
            context = context,
            resId = R.drawable.ic_bank,
            scale = scale
        )
    }

    val markerIcon = remember {
        createMarkerIcon(context, R.drawable.ic_bank)
    }

    var hasPermission by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var routePoints by remember { mutableStateOf<List<LatLng>>(emptyList()) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val locationRequest = remember {
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000
        ).setMinUpdateIntervalMillis(1500).build()
    }

    val locationCallback = remember {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                val latLng = LatLng(location.latitude, location.longitude)

                currentLocation = latLng

                scope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    )
                }
            }
        }
    }

    DisposableEffect(hasPermission) {
        if (hasPermission) {
            if (ActivityCompat.checkSelfPermission(
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
        }

        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

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
            branches.forEach { area ->
                Marker(
                    state = MarkerState(position = LatLng(area.latitude, area.longitude)),
                    title = area.name,
                    snippet = "Sandton Area",
                    icon = animatedIcon
                )
            }
        }

        BranchSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.TopCenter)
        )
    }
}

fun openGoogleMapsNavigation(context: Context, lat: Double, lng: Double) {
    val uri = Uri.parse("google.navigation:q=$lat,$lng")

    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps") // ensures Google Maps opens
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()

        // fallback if Google Maps isn't installed
        val fallbackUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng")
        val fallbackIntent = Intent(Intent.ACTION_VIEW, fallbackUri)
        context.startActivity(fallbackIntent)
    }
}

// -----------------------------
// Marker icon
// -----------------------------
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

fun createMarkerIcon(
    context: Context,
    @DrawableRes resId: Int,
    scale: Float = 1f
): BitmapDescriptor {

    val drawable = ContextCompat.getDrawable(context, resId)
        ?: return BitmapDescriptorFactory.defaultMarker()

    val width = (drawable.intrinsicWidth * scale).toInt().coerceAtLeast(1)
    val height = (drawable.intrinsicHeight * scale).toInt().coerceAtLeast(1)

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
@Composable
fun BranchSearchBar(modifier: Modifier = Modifier) {

    var query by remember { mutableStateOf("") }

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
                onValueChange = { query = it },
                placeholder = { Text("Search branches...") },
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

val branches = listOf(
    BankBranchDetail(name="Sandton City", latitude = -26.1076, longitude = 28.0567),
    BankBranchDetail("Nelson Mandela Square", latitude = -26.1079,longitude = 28.0569),
    BankBranchDetail("Rosebank Mall", latitude = -26.1456,longitude = 28.0436),
    BankBranchDetail("Morningside", latitude = -26.0937,longitude = 28.0583),
    BankBranchDetail("Bryanston", latitude =-26.0489,longitude= 28.0287),
    BankBranchDetail("Benmore Gardens", latitude = -26.1024, longitude=28.0618),
    BankBranchDetail("Grayston Drive", latitude = -26.0975,longitude = 28.0542),
    BankBranchDetail("Illovo", latitude = -26.1302, longitude =28.0516),
    BankBranchDetail("Woodmead", latitude = -26.0449,longitude = 28.1021),
    BankBranchDetail("Rivonia", latitude = -26.0535,longitude = 28.0590)
)