package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.DateTimeRow
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.util.Calendar

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
                    Text(text = stringResource(id = R.string.button_confirm))
                }
            }
        }
    ) {
            innerPadding ->
        RouteAddBody(
            fuelingUiState = viewModel.routeUiState,
            onValueChange = viewModel::updateUiState,
            //viewModel =viewModel,
            modifier = Modifier
                .padding(
//                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            viewModel = viewModel)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouteAddBody(
    fuelingUiState: RouteAddUiState,
    onValueChange: (RouteAsUi)->Unit,
    viewModel: RouteModifieInterface,
    modifier: Modifier) {

    val detail = fuelingUiState.details

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        var startTimeNotModified by remember {
            mutableStateOf(true)
        }
        var finishTimeNotModifie by remember {
            mutableStateOf(true)
        }
            OutlinedTextField(value = detail.title,
                onValueChange = { onValueChange(detail.copy(title = it)) },
                label = { Text(text = stringResource(R.string.in_field_route_name_of_route)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
            )

            OutlinedTextField(value = detail.distance,
                onValueChange = { viewModel.updatedDistance(it) },
                label = { Text(text = stringResource(R.string.in_field_route_distance)) },
                leadingIcon = {Text(text = stringResource(id = R.string.unit_distance_short))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done)
            )


            OutlinedTextField(value = detail.finish_odometer,
                onValueChange = { onValueChange(detail.copy(finish_odometer = it)) },
                label = { Text(text = stringResource(R.string.in_field_route_end_state_odometer)) },
                leadingIcon = {Text(text = stringResource(id = R.string.unit_distance_short))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
            )
        FlowRow (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
            Column{
                Text(text = stringResource(R.string.timepicker_route_start_time))
                DateTimeRow(
                    initCalendar = detail.start_time,
                    onValueChange = { calendar ->
                        val newStartCalendar : Calendar = Calendar.getInstance()
                        newStartCalendar.timeInMillis = calendar.timeInMillis
                        val newFinistCalendar = Calendar.getInstance()
                        newFinistCalendar.timeInMillis = detail.finish_time.timeInMillis
                        startTimeNotModified = false
                        if (finishTimeNotModifie) {
                            newFinistCalendar.timeInMillis = calendar.timeInMillis
                        }
                        val newDetail = detail.copy(start_time = newStartCalendar, finish_time = newFinistCalendar)
                        onValueChange(newDetail)
                                    },
                    modifier = Modifier,
                )}
            Column() {
                Text(text = stringResource(R.string.timepicker_route_finish_time))
                DateTimeRow(
                    initCalendar = detail.finish_time,
                    onValueChange = { calendar ->
                        var newdetail = detail.copy(finish_time =  calendar)
                        finishTimeNotModifie = false
                        if(startTimeNotModified){
                            newdetail = newdetail.copy(start_time = calendar)
                        }
                        onValueChange(newdetail)
                                    },
                    modifier = Modifier
                )
            }
        }

        OutlinedTextField(value = detail.start_point,
            onValueChange = { onValueChange(detail.copy(start_point = it)) },
            label = { Text(text = stringResource(R.string.in_field_route_from)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
        )
        OutlinedTextField(value = detail.finish_point,
            onValueChange = { onValueChange(detail.copy(finish_point = it)) },
            label = { Text(text = stringResource(R.string.in_field_route_to)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
        )

        }
}
