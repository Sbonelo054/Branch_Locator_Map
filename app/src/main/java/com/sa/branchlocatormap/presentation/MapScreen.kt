package com.sa.branchlocatormap.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.sa.branchlocatormap.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val sandton = LatLng(-26.1076, 28.0567)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sandton, 8f)
    }
    val context = LocalContext.current
    val markerIcon = remember {
        createMarkerIcon(context, R.drawable.bank_branches)
    }

    Box(modifier = modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState
        ) {
            sandtonAreas.forEach { area ->
                Marker( state = MarkerState(position = area.location),
                    title = area.name,
                    snippet = "Sandton Area",
                    icon = markerIcon
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

fun createMarkerIcon(
    context: Context,
    @DrawableRes resId: Int
): BitmapDescriptor {

    val drawable = ContextCompat.getDrawable(context, resId)
        ?: return BitmapDescriptorFactory.defaultMarker()

    drawable.setBounds(
        0, 0,
        drawable.intrinsicWidth.coerceAtLeast(1),
        drawable.intrinsicHeight.coerceAtLeast(1)
    )

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth.coerceAtLeast(1),
        drawable.intrinsicHeight.coerceAtLeast(1),
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
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
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = {
                    Text("Search branches...")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

// -----------------------------
// Data Model
// -----------------------------
data class SandtonArea(
    val name: String,
    val location: LatLng
)

// -----------------------------
// Sandton Locations List
// -----------------------------
val sandtonAreas = listOf(
    SandtonArea(
        name = "Sandton City",
        location = LatLng(-26.1076, 28.0567)
    ),
    SandtonArea(
        name = "Nelson Mandela Square",
        location = LatLng(-26.1079, 28.0569)
    ),
    SandtonArea(
        name = "Rosebank Mall (near Sandton zone)",
        location = LatLng(-26.1456, 28.0436)
    ),
    SandtonArea(
        name = "Morningside",
        location = LatLng(-26.0937, 28.0583)
    ),
    SandtonArea(
        name = "Bryanston",
        location = LatLng(-26.0489, 28.0287)
    ),
    SandtonArea(
        name = "Benmore Gardens",
        location = LatLng(-26.1024, 28.0618)
    ),
    SandtonArea(
        name = "Grayston Drive",
        location = LatLng(-26.0975, 28.0542)
    ),
    SandtonArea(
        name = "Illovo (Sandton border)",
        location = LatLng(-26.1302, 28.0516)
    ),
    SandtonArea(
        name = "Woodmead",
        location = LatLng(-26.0449, 28.1021)
    ),
    SandtonArea(
        name = "Rivonia",
        location = LatLng(-26.0535, 28.0590)
    )
)