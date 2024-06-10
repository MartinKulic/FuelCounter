package com.example.vapmzsem.ui.Fueling

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
    val routeWithArgs = "${route}/{$fuelingIdArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingEditScreen(
    navigateBack : ()->Unit,
    onNavigateUp: () -> Unit = navigateBack,
    onModificationConfirm: () -> Unit = navigateBack,
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
        },
        bottomBar = {
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center ){
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.saveFueling()
                        onModificationConfirm()
                    } },
                    Modifier.fillMaxWidth(), enabled = viewModel.fuelingUiState.isEntryValid) {
                    Text(text = stringResource(id = R.string.button_confirm))
                }
            }
        }
    ) {
            innerPadding ->
        FuelingAddBody(
            fuelingUiState = viewModel.fuelingUiState,
            onValueChange = viewModel::updateUiState,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                //.verticalScroll(rememberScrollState())
                .fillMaxWidth())
    }
}