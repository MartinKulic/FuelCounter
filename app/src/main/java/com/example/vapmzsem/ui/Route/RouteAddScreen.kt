package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
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
                //.verticalScroll(rememberScrollState())
                .fillMaxWidth())
    }
}

@Composable
fun RouteAddBody(fuelingUiState: RouteAddUiState, onValueChange: (RouteAsUi)->Unit, viewModel: RouteAddViewModel, modifier: Modifier) {
    val detail = fuelingUiState.details
    Column(modifier = modifier) {
        OutlinedTextField(value = detail.title,
            onValueChange = {onValueChange(detail.copy(title = it))},
            label = {Text(text = "Názov cesty")}
            )
    }
    Column(modifier = modifier) {
        OutlinedTextField(value = detail.distance,
            onValueChange = {onValueChange(detail.copy(distance = it))},
            label = {Text(text = "Názov cesty")}
        )
    }
    Column(modifier = modifier) {
        OutlinedTextField(value = detail.finish_odometer,
            onValueChange = {onValueChange(detail.copy(finish_odometer = it))},
            label = {Text(text = "Názov cesty")}
        )
    }
}
