package com.example.travelPlanAndroidVer
import TripsScreen
//import com.example.travelPlanAndroidVer.Pages.NewTrip.AddTripScreen
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import plan.Pages.NewStage.NewStage
import plan.Pages.NewTrip.AddTripScreen

@Composable
fun Main() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "trips") {
        composable("trips") { TripsScreen(navController = navController) }
        composable("addTrip") { AddTripScreen(navController = navController) }
        composable("addStage") { NewStage(navController = navController)}
    }
}
