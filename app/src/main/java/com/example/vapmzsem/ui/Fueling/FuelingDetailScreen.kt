package com.example.vapmzsem.ui.Fueling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import com.example.vapmzsem.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Currency
import java.util.Locale

object FuelingDetailDestination : NavigationDestination{
    override val route = "fueling_detail"
    override val titleRes = R.string.fueling_detail_screen_title
    const val fuelingIdArg = "fuelingId"
    val routeWithArgs = "$route/{$fuelingIdArg}"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingDetailScreen(
    onEditClick : (Int) -> Unit ={},
    navigateBack : ()->Unit,
    onNavigateUp: () -> Unit = navigateBack,
    modifier: Modifier = Modifier,
    viewModel: FuelingDetailViewModel  = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(FuelingDetailDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = { FuelingDetailBottomBar(
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.delete()
                    navigateBack()
                }
            } ,
            onEditClick = {onEditClick(viewModel.uiState.value.id)}
        )}
    ) {
        innerPadding ->
            FuelingDetailBody(details = uiState.value, modifier= Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = (15.dp),
                    end = (15.dp)
                )
            )

    }
}
@Composable
fun FuelingDetailBottomBar(
    onDeleteClick : () -> Unit,
    onEditClick: () -> Unit
)
{
    var deleteConfirmation by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom){
        OutlinedButton(onClick = { deleteConfirmation = true }, modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(text= stringResource(R.string.button_delete))
        }
        Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth() ) {
            Text(text = stringResource(R.string.button_edit))
        }
    }

    if(deleteConfirmation){
        DeleteConfirmationDialog(
            onConfirm = {
                deleteConfirmation = false
                onDeleteClick()
                        },
            onReject = {
                deleteConfirmation = false
            })
    }
}
@Composable
fun FuelingDetailBody(
    details : FuelingAsUi,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        val cenaZaL = ((details.total_price.toFloatOrNull()?:1f)/(details.quantity.toFloatOrNull()?:1f))
        DetailItem(title = stringResource(id = R.string.in_field_fueling_price), value = details.total_price, unit = Currency.getInstance(Locale.getDefault()).symbol)
        DetailItem(title = stringResource(id = R.string.in_field_fueling_quantity) , value = details.quantity)
        DetailItem(title = stringResource(R.string.desc_price_per_volume_unit), value = DecimalFormat(
            "##.0000", DecimalFormatSymbols(
                Locale.getDefault()
            )).format(cenaZaL),
            unit = stringResource(
                R.string.unit_price_per_volume_unit,
                Currency.getInstance(Locale.getDefault()).symbol,
                stringResource(id = R.string.unit_volume_short)
            )
        )
        DetailItem(title = stringResource(id = R.string.in_field_fueling_odometer) , value = details.odometter, unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(R.string.desc_distance_traveled), value = details.distance, unit = stringResource(
            id = R.string.unit_distance_short
        ))
        DetailItem(title = stringResource(R.string.desc_average_consumption), value = details.average_fuel_consumption, unit = stringResource(
            R.string.unit_fuel_consumption
        )
        )
        DetailItem(title = stringResource(id = R.string.in_switch_fueling_full_tank), value = if (details.full_tank) stringResource(
            R.string.yes
        ) else stringResource(R.string.no)
        )
        DetailItem(title = stringResource(R.string.desc_date_and_time), value = SimpleDateFormat(
            stringResource(R.string.value_format_full_date_at_time), Locale.getDefault()).format(details.time.timeInMillis))
        DetailItem(title = stringResource(id = R.string.in_field_fueling_fueling_station) , value = details.fueling_Station)
        DetailItem(title = stringResource(id = R.string.in_field_fueling_type_of_fuel) , value = details.fuel_type)

    }
}

@Composable
fun DetailItem(
    title : String,
    value : String,
    unit : String = ""
){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "${title}: ", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(0.70f))

        Text(text = "$value $unit", textAlign = TextAlign.Right)
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm : () -> Unit,
    onReject : () -> Unit
){
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.text_delete_dialog)) },
        modifier = Modifier,
        dismissButton = {
            TextButton(onClick =  onReject ) {
                Text(text = stringResource(id = R.string.no))
            }
        },
        confirmButton = { TextButton(onClick =  onConfirm ) {
            Text(text = stringResource(id = R.string.yes))
        }}

    )

}

@Preview
@Composable
fun FuelingDetailScreenPreview(){
    AppTheme {
        Surface {
            FuelingDetailBody(
                modifier = Modifier.fillMaxSize(),
                details = FuelingAsUi(quantity = "36.45", total_price = "45.23", full_tank = false, fuel_type = "Natural95", fueling_Station = "Rajec Shell", time = Calendar.getInstance(), odometter = "74864")
            )
        }
    }
}