package com.example.vapmzsem.ui.Statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.DetailItem
import com.example.vapmzsem.ui.navigation.NavigationDestination
import java.util.Currency
import java.util.Locale

object StatisticsScreenDestination : NavigationDestination{
    override val route: String = "statistic"
    override val titleRes: Int = R.string.tatistina_screen_title
}

@Composable
fun StatisticScreen(
    viewModel : StatisticViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val uiState : StatisticUiState = viewModel.uiState.collectAsState().value
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(15.dp)) {
        DetailItem(title = "Priemerná spotreba", value = uiState.averageConsummption, unit = "l/100km")
        DetailItem(title = "Priemerná cena paliva", value = uiState.avarageFuelPrice, unit = "${
            Currency.getInstance(
                Locale.getDefault()).symbol}/l")
        DetailItem(title = "Priemerné množstvo kúpeného paliva", value = uiState.averageQuantity, unit = "l")
        DetailItem(title = "Priemerne zaplatené za tankovanie", value = uiState.averagePayedForFuel, unit = Currency.getInstance(Locale.getDefault()).symbol)
        DetailItem(title = "Priemerné vzdialonosť medzi tankovaniami", value = uiState.averageDistanceBetweenFuelings, unit = "km")
        DetailItem(title = "Celkovo prejdene", value = uiState.totalDistanceTraveled, unit = "km")
        DetailItem(title = "Celkovo paliva kúpeného", value = uiState.totalFuelBuyed, unit = "l")

    }
}