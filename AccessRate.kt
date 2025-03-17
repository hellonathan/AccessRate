package com.example.accessrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : ComponentActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setContent {
            AccessibilityRatingApp(mapView)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val location = LatLng(-37.8136, 144.9631) // Example: Melbourne
        googleMap.addMarker(MarkerOptions().position(location).title("Sample Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }
}

@Composable
fun AccessibilityRatingApp(mapView: MapView) {
    var rating by remember { mutableStateOf(0f) }
    var comment by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Rate Accessibility", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(rating) { rating = it }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Leave a comment") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Save to backend */ }) {
            Text("Submit")
        }
    }
}

@Composable
fun RatingBar(rating: Float, onRatingChanged: (Float) -> Unit) {
    Row {
        for (i in 1..5) {
            IconButton(onClick = { onRatingChanged(i.toFloat()) }) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star Rating"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccessibilityRatingApp(MapView(null))
}
