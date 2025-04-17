package com.example.travelPlanAndroidVer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

data class Stage(
    val type: String,
    val title: String,
    val description: String,
    val uploadedFiles: List<String>,
    val date: LocalDate,
    val cost: Double
)
data class Trip(val location: String, val country: String, val stages: List<Stage> = emptyList())
class TripsViewModel : ViewModel() {

    private val _trips = MutableStateFlow(
        listOf(
            Trip("East bumblefuck", "UK"),
            Trip("West bumblefuck", "UK"),
            Trip("Muhosransk", "Russia"),
            Trip("Le Nizhnye Stydi", "France")
        )
    )

    val trips: StateFlow<List<Trip>> = _trips

    fun addTrip(trip: Trip) {
        _trips.value = _trips.value + trip
    }

    fun removeTrip(trip: Trip) {
        _trips.value = _trips.value - trip
    }
}