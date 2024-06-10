package com.example.vapmzsem.ui.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.FuelingItem
import com.example.vapmzsem.ui.Fueling.MyItemDetailDisplay
import com.example.vapmzsem.ui.Route.RouteAsUi
import com.example.vapmzsem.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Locale

object HomeScreenDestination : NavigationDestination{
    override val route: String = "home"

    override val titleRes: Int = R.string.home_screen_title

}

@Composable
fun HomeScreen(
    onFuelingClicked : (Int)->Unit,
    onRouteClicked : (Int)->Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState by viewModel.homeUiState.collectAsState()
    LazyColumn(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        for (myContainer in uiState.data){
            item {
                Column (modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer).border(2.dp, color = Color(0xFF3E7B9F))){
                    myContainer.routes.forEach { routeUi ->
                        RouteItem(
                            item = routeUi,
                            Modifier
                                .padding(top = 8.dp, start = 30.dp, end = 30.dp)
                                .clickable { onRouteClicked(routeUi.id) })
                    }
                    FuelingItem(
                        item = myContainer.fueling,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clickable { onFuelingClicked(myContainer.fueling.id) })
                }
            }
        }
    }
}

@Composable
fun RouteItem(item : RouteAsUi, modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        //Spacer(Modifier)

            ElevatedCard (
                modifier = Modifier.padding(5.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                if (item.start_point.isNotEmpty() || item.finish_point.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${item.start_point} -> ${item.finish_point}",
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = SimpleDateFormat(
                                "dd.MM.yyyy",
                                Locale.getDefault()
                            ).format(item.start_time.timeInMillis)
                        )
                        Text(
                            text = SimpleDateFormat(
                                "hh:mm:ss",
                                Locale.getDefault()
                            ).format(item.finish_time.timeInMillis - item.start_time.timeInMillis)
                        )
                    }

                    MyItemDetailDisplay(unit = "km", value = item.distance, widthfill = 0.28f)
                    MyItemDetailDisplay(unit = "l", value = item.fuel_used, widthfill = 0.35f)
                    MyItemDetailDisplay(
                        unit = Currency.getInstance(Locale.getDefault()).symbol,
                        value = item.cost_of_route,
                        widthfill = 0.6f
                    )

                }
            }

    }
}