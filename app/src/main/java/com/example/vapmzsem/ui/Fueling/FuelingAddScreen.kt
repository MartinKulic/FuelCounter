package com.example.vapmzsem.ui.Fueling

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import com.example.vapmzsem.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Currency
import java.util.Date
import java.util.Locale

object FuelingAddScreenDestination : NavigationDestination{
    override val route: String = "fueling_add"
    override val titleRes: Int = R.string.fueling_add_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingAddScreen(
    onNavigateBack : ()->Unit = {},
    onNavigateUp: ()->Unit = onNavigateBack,
    viewModel: FuelingAddViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(FuelingAddScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center ){
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.saveFueling()
                        onNavigateBack()
                    } },
                    Modifier.fillMaxWidth(), enabled = viewModel.fuelingUiState.isEntryValid) {
                    Text(text = stringResource(R.string.button_confirm))
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
fun FuelingAddBody(
    fuelingUiState: FuelingUiState,
    onValueChange : (FuelingAsUi)->Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

        FuelingAddForm(details = fuelingUiState.details, onValueChange = onValueChange, previousOdometerValue = fuelingUiState.lastOdometer)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingAddForm(
    details : FuelingAsUi,
    onValueChange: (FuelingAsUi) -> Unit,
    previousOdometerValue : String? = null
){
    /*TODO Aby nebolo mozne pridat zaporne quantity a cenu*/
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = details.quantity,
            label = { Text(stringResource(R.string.in_field_fueling_quantity)) },
            onValueChange = { onValueChange(details.copy(quantity = it)) },
            leadingIcon = { Text(stringResource(R.string.unit_volume_short)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = details.total_price,
            label = {Text(stringResource(R.string.in_field_fueling_price))},
            onValueChange = {onValueChange(details.copy(total_price = it))},
            leadingIcon = {Text(Currency.getInstance(Locale.getDefault()).symbol)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = details.odometter,
            label = {Text(stringResource(R.string.in_field_fueling_odometer))},
            supportingText = {if (previousOdometerValue != null) {
                Text(text = stringResource(
                    R.string.in_field_fueling_odometer_support_text,
                    previousOdometerValue,
                    stringResource(R.string.unit_distance_short)
                ))
            } },
            onValueChange = {onValueChange(details.copy(odometter = it))},
            leadingIcon = {Text(stringResource(id = R.string.unit_distance_short))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )

        Row (horizontalArrangement = Arrangement.Center) {
            DateTimeRow(
                initCalendar = details.time,
                onValueChange = { onValueChange(details.copy(time = it)) })
        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(R.string.in_switch_fueling_full_tank))
            Switch (modifier = Modifier.padding(start = 15.dp, end = 20.dp),
                checked = details.full_tank,
                onCheckedChange = { onValueChange(details.copy(full_tank = it)) }
            )
            if (!details.full_tank) {
                Text(text = stringResource(R.string.in_switch_fueling_warming_text), color = Color.Red, fontSize = 12.sp)
            }
        }

        OutlinedTextField(
            value = details.fuel_type,
            label = {Text(stringResource(R.string.in_field_fueling_type_of_fuel))},
            onValueChange = {onValueChange(details.copy(fuel_type = it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = details.fueling_Station,
            label = {Text(stringResource(R.string.in_field_fueling_fueling_station))},
            onValueChange = {onValueChange(details.copy(fueling_Station = it))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Go)
        )

    }

}

/**
 * Komponent pre vyber datumu a casu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeRow(
    initCalendar: Calendar,
    onValueChange: (Calendar) -> Unit,
    modifier: Modifier = Modifier,
){
    var datePickerVisible by remember { mutableStateOf(false) }
    var timePickerVisible by remember { mutableStateOf(false) }
    val calendar = remember { Calendar.getInstance() }

    Row (modifier = modifier){
        Column (modifier = Modifier
            .clickable(
                onClick = { datePickerVisible = true })
            .padding(5.dp)
        ) {
            OutlinedCard (
            ){
                Text(text = stringResource(R.string.time_picker_date), fontSize = 15.sp, modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                Text(text =  SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(initCalendar.timeInMillis ?: Date()), //details.time.let { SimpleDateFormat("dd.MM.yyy").format(it) ?: ""
                    modifier=Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp,))
            }
        }

        Column (modifier = Modifier
            .clickable(onClick = { timePickerVisible = true })
            .padding(5.dp)
        ) {
            OutlinedCard (
            ){
                Text(text = stringResource(R.string.time_picker_time), fontSize = 15.sp, modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                Text(text = initCalendar.time.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it.time ?: Date()) },
                    modifier=Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp,))
            }
        }
    }

    if(datePickerVisible){
        val dateState  = rememberDatePickerState( initialSelectedDateMillis = initCalendar.timeInMillis )
        Column {
            DatePickerDialog(
                dismissButton = {
                    Button(onClick = { datePickerVisible = false }) {
                        Text(text = stringResource(R.string.button_cancel))
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val calendarTime : Triple<Int,Int, Int> = Triple<Int, Int, Int> (calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND))
                        calendar.timeInMillis = dateState.selectedDateMillis ?: 0
                        calendar.set(Calendar.HOUR_OF_DAY, calendarTime.first)
                        calendar.set(Calendar.MINUTE, calendarTime.second)
                        calendar.set(Calendar.SECOND, calendarTime.third)

                        onValueChange(calendar)
                        datePickerVisible = false
                    }) {
                        Text(text = stringResource(id = R.string.button_confirm))
                    }
                },
                onDismissRequest = { datePickerVisible = false }
            ) {
                DatePicker(state = dateState)
            }
        }
    }
    if(timePickerVisible){
        TimePickerDialog(
            LocalContext.current,
            {
                _, hours, minutes ->
                calendar.set(Calendar.HOUR_OF_DAY, hours)
                calendar.set(Calendar.MINUTE, minutes)
                onValueChange(calendar)
                timePickerVisible = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FuelingAddBodyPreview(){
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold (topBar = {
                MyTopAppBar(title = stringResource(FuelingAddScreenDestination.titleRes),
                    canNavigateBack = true,
                    navigateUp = { }
                )
            }) {
                    innerPadding ->
                    FuelingAddBody(fuelingUiState = FuelingUiState(true,FuelingAsUi(quantity = "50.4", total_price = "72.54", full_tank = true, fuel_type = "Natural95", odometter = "90123")),
                        onValueChange = {},
                        modifier = Modifier
                            .padding(
                                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                                top = innerPadding.calculateTopPadding()
                            )
                            //.verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                        )

            }
        }
    }
}