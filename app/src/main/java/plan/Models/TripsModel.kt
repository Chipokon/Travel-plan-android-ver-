package com.example.travelPlanAndroidVer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Trip(val location: String, val country: String)

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