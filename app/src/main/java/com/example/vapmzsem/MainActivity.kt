package com.example.vapmzsem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vapmzsem.data.Fueling
import com.example.vapmzsem.ui.Home.FuelingScreen
import com.example.vapmzsem.ui.theme.AppTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FuelingScreen(itemList = listOf(
                        Fueling(id_F = 0, quantity = 50.5f, total_price = 60.99f, full_tank = true, fuel_type = null, fueling_Station = null, time = Date(), odometter = null ),
                        Fueling(id_F = 1, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 2, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 3, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 4, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 5, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 6, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 7, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 8, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 9, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 10, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 11, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 12, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 13, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 14, quantity = 42.87f, total_price = 50.12f),
                        Fueling(id_F = 15, quantity = 60.0f, total_price = 100f),
                        Fueling(id_F = 16, quantity = 45.99f, total_price = 62.71f, fuel_type = "Natural95", full_tank = true, fueling_Station = "Rajec začiaton Shell", odometter = 92015)
                    ))
                }
            }
        }
    }
}