package com.example.travelPlanAndroidVer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Main(viewModel: TripsViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    val trips by viewModel.trips.collectAsState()

    val filteredTrips = trips.filter {
        it.location.contains(searchQuery, ignoreCase = true) ||
                it.country.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            // Top Bar без кнопки Add
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(48.dp)) // пустое место вместо Add
                Text(
                    text = "My trips",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* TODO: Show info */ }) {
                    Icon(Icons.Default.Info, contentDescription = "Info")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Trip list
            Surface(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(filteredTrips) { trip ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text("${trip.location}, ${trip.country}")
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

        // FAB в правом нижнем углу
        FloatingActionButton(
            onClick = {
                viewModel.addTrip(Trip("New place", "Nowhere"))
            },
            modifier = Modifier
                .align(alignment = androidx.compose.ui.Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}