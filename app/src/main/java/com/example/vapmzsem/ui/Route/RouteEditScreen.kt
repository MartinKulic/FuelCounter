package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object RouteEditDestination :NavigationDestination {
    override val route: String = "route_edit"
    override val titleRes: Int = R.string.route_edit_screen_title
    const val routeIdArg = "routeId"
    val routeWithArgs = "${route}/{$routeIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteEditScreen(
    navigateBack : ()->Unit,
    navigateUp: ()->Unit = navigateBack,
    onModificationConfirm : ()->Unit = navigateBack,
    viewModel : RouteEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = { MyTopAppBar(title = stringResource(id = RouteEditDestination.titleRes), canNavigateBack = true, navigateUp = navigateUp)},
        bottomBar = {
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center ){
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.updateRoute()
                    onModificationConfirm()
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
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                //.verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}