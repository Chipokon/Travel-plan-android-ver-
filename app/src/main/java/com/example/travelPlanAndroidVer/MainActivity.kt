package com.example.travelPlanAndroidVer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.travelPlanAndroidVer.ui.theme.TravelPlanAndroidVerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelPlanAndroidVerTheme {
                Main()
            }
        }
    }
}