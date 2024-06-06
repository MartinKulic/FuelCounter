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
        DetailItem(title = "Názov", value = detail.title)
        DetailItem(title = "Prejdená vzdialenosť", value = detail.distance, unit = "km")
        DetailItem(title = "Čas začiatku", value =  SimpleDateFormat("EEE dd.MM.yyyy 'o' HH:mm:ss", Locale.getDefault()).format(detail.start_time.timeInMillis))
        DetailItem(title = "Začiatok", value = detail.finish_point)
        DetailItem(title = "Čas príjazdu", value =  SimpleDateFormat("EEE dd.MM.yyyy 'o' HH:mm:ss", Locale.getDefault()).format(detail.finish_time.timeInMillis))
        DetailItem(title = "Cieľ", value = detail.finish_point)
        DetailItem(title = "Počiatočný stav odometra", value = ((detail.finish_odometer.toFloatOrNull()?:0f) - (detail.distance.toFloatOrNull()?:0f)).toString())
        DetailItem(title = "Konečný stav odometra", value = detail.finish_odometer)
        DetailItem(title = "Predpokladané potrebované palivo", value = detail.fuel_used, unit = "l")
        DetailItem(title = "Predpokladaná cena cesty", value = detail.cost_of_route, unit = Currency.getInstance(Locale.getDefault()).symbol)
    }
}