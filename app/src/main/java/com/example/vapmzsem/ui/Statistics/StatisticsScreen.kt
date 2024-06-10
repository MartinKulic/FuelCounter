package com.example.vapmzsem.ui.Statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        DetailItem(title = stringResource(id = R.string.desc_average_consumption) , value = uiState.averageConsummption, unit = stringResource(
            R.string.unit_fuel_consumption
        ))
        DetailItem(title = stringResource(R.string.desc_average_cost_of_fuel), value = uiState.avarageFuelPrice, unit = stringResource(
            R.string.unit_price_per_volume_unit,
            Currency.getInstance(Locale.getDefault()).symbol,
            stringResource(id = R.string.unit_volume_short)))
        DetailItem(title = stringResource(R.string.desc_average_quantity_of_buyed_fuel), value = uiState.averageQuantity, unit = stringResource(
            id = R.string.unit_volume_short
        ))
        DetailItem(title = stringResource(R.string.desc_average_payed_for_fuel), value = uiState.averagePayedForFuel, unit = Currency.getInstance(Locale.getDefault()).symbol)
        DetailItem(title = stringResource(R.string.desc_average_distance_traveled_on_one_fueling), value = uiState.averageDistanceBetweenFuelings, unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(R.string.desc_total_distance_traveled), value = uiState.totalDistanceTraveled, unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(R.string.desc_total_fuel_buyed), value = uiState.totalFuelBuyed, unit = stringResource(
            id = R.string.unit_volume_short
        ))

    }
}