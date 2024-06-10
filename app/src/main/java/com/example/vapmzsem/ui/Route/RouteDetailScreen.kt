package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.DetailItem
import com.example.vapmzsem.ui.Fueling.FuelingDetailBottomBar
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Locale

object RouteDetailDestinantion : NavigationDestination{
    override val route: String = "route_detail"
    override val titleRes: Int = R.string.route_detail_screen_title
    const val routeIdArg = "routeId"
    val routeWithArgs = "$route/{$routeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailScreen (
    onEditClick : (Int) -> Unit = {},
    navigateBack : ()->Unit,
    onNavigateUp : ()->Unit = navigateBack,
    modifier: Modifier = Modifier,
    viewModel : RouteDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            MyTopAppBar(title = stringResource(id = RouteDetailDestinantion.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp)},
        bottomBar = { FuelingDetailBottomBar(onDeleteClick = {
            coroutineScope.launch {
                viewModel.delete()
                navigateBack()
                }
            },
            onEditClick = {onEditClick(viewModel.uiState.value.id)}
        ) }
    ){
        innerPading ->
            RouteDetailBody(detail = viewModel.uiState.collectAsState().value,
                modifier = Modifier
                    .padding(top = innerPading.calculateTopPadding(), start = 10.dp, end = 10.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .fillMaxWidth()
            )
    }
}

@Composable
fun RouteDetailBody(
    detail : RouteAsUi,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        DetailItem(title = stringResource(R.string.desc_route_name), value = detail.title)
        DetailItem(title = stringResource(R.string.desc_route_distance_traveled), value = detail.distance, unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(id = R.string.timepicker_route_start_time), value =  SimpleDateFormat(
            stringResource(id = R.string.value_format_full_date_at_time), Locale.getDefault()).format(detail.start_time.timeInMillis))
        DetailItem(title = stringResource(R.string.desc_route_start_point), value = detail.finish_point)
        DetailItem(title = stringResource(R.string.desc_route_time_of_arival), value =  SimpleDateFormat(
            stringResource(id = R.string.value_format_full_date_at_time), Locale.getDefault()).format(detail.finish_time.timeInMillis))
        DetailItem(title = stringResource(R.string.desc_rooute_finish_point), value = detail.finish_point)
        DetailItem(title = stringResource(R.string.desc_route_start_odometer), value = ((detail.finish_odometer.toFloatOrNull()?:0f) - (detail.distance.toFloatOrNull()?:0f)).toString(), unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(R.string.desc_route_finish_odometer), value = detail.finish_odometer, unit = stringResource(
            id = R.string.unit_distance_short))
        DetailItem(title = stringResource(R.string.desc_route_assumed_fuel_used), value = detail.fuel_used, unit = stringResource(
            id = R.string.unit_volume_short
        ))
        DetailItem(title = stringResource(R.string.desc_route_assumed_fuel_consumption), value = detail.fuel_consumption, unit = stringResource(
            id = R.string.unit_fuel_consumption
        ))
        DetailItem(title = stringResource(R.string.desc_route_assumed_price_of_route), value = detail.cost_of_route, unit = Currency.getInstance(Locale.getDefault()).symbol)
    }
}