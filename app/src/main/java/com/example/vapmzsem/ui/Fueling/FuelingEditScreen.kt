package com.example.vapmzsem.ui.Fueling

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object FuelingEditScreenDestination : NavigationDestination{
    override val route: String = "fueling_edit"
    override val titleRes: Int = R.string.uprav_tankovanie
    const val fuelingIdArg = "fuelingID"
    val routeWithArgs = "${FuelingEditScreenDestination.route}/{${FuelingEditScreenDestination.fuelingIdArg}}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingEditScreen(
    navigateBack : ()->Unit,
    onNavigateUp: () -> Unit = navigateBack,
    modifier: Modifier = Modifier,
    viewModel: FuelingEditViewModel  = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(FuelingEditScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {
            innerPadding ->
        FuelingAddBody(
            fuelingUiState = viewModel.fuelingUiState,
            onValueChange = viewModel::updateUiState,
            onConfirmClick = {
                coroutineScope.launch {
                    viewModel.saveFueling()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                //.verticalScroll(rememberScrollState())
                .fillMaxWidth())
    }
}