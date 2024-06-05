package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.DateTimeRow
import com.example.vapmzsem.ui.Fueling.FuelingAddBody
import com.example.vapmzsem.ui.Fueling.FuelingAddScreenDestination
import com.example.vapmzsem.ui.Fueling.FuelingAddViewModel
import com.example.vapmzsem.ui.Fueling.FuelingAsUi
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object RouteAddScreenDestination : NavigationDestination{
    override val route: String = "route_add"
    override val titleRes: Int = R.string.nov_cesta
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteAddScreen(
    onNavigateBack : ()->Unit = {},
    onNavigateUp: ()->Unit = onNavigateBack,
    viewModel: RouteAddViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(RouteAddScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center ){
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.saveRoute()
                        onNavigateBack()
                    } },
                    Modifier.fillMaxWidth(), enabled = viewModel.routeUiState.isEntryValid) {
                    Text(text = "Potvrď")
                }
            }
        }
    ) {
            innerPadding ->
        RouteAddBody(
            fuelingUiState = viewModel.routeUiState,
            onValueChange = viewModel::updateUiState,
            viewModel =viewModel,
            modifier = Modifier
                .padding(
//                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth())
    }
}

@Composable
fun RouteAddBody(fuelingUiState: RouteAddUiState, onValueChange: (RouteAsUi)->Unit, viewModel: RouteAddViewModel, modifier: Modifier) {

    val detail = fuelingUiState.details

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(value = detail.title,
                onValueChange = { onValueChange(detail.copy(title = it)) },
                label = { Text(text = "Názov cesty") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )

            OutlinedTextField(value = detail.distance,
                onValueChange = { onValueChange(detail.copy(distance = it)) },
                label = { Text(text = "Vzdialenost") },
                leadingIcon = {Text(text = "km")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
            )


            OutlinedTextField(value = detail.finish_odometer,
                onValueChange = { onValueChange(detail.copy(finish_odometer = it)) },
                label = { Text(text = "Konečný stav odometru") },
                leadingIcon = {Text(text = "km")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )
        Row (modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
             Column{
                Text(text = "Čas začiatku")
                DateTimeRow(
                    initCalendar = detail.start_time,
                    onValueChange = { onValueChange(detail.copy(start_time = it)) },
                    modifier = Modifier)}
            Column() {
                Text(text = "Čas konca")
                DateTimeRow(
                    initCalendar = detail.finish_time,
                    onValueChange = { onValueChange(detail.copy(finish_time = it)) },
                    modifier = Modifier
                )
            }
        }

        OutlinedTextField(value = detail.start_point,
            onValueChange = { onValueChange(detail.copy(start_point = it)) },
            label = { Text(text = "Z") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
        )
        OutlinedTextField(value = detail.finish_point,
            onValueChange = { onValueChange(detail.copy(finish_point = it)) },
            label = { Text(text = "Do") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
        )

        }
}
