package com.example.vapmzsem.ui.Home

import android.app.TimePickerDialog
import android.health.connect.datatypes.units.Volume
import android.telecom.Call.Details
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.data.Converters
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import com.example.vapmzsem.ui.theme.AppTheme
import java.text.SimpleDateFormat
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
    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(FuelingAddScreenDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) {
        innerPadding ->
            FuelingAddBody(
                fuelingUiState = viewModel.fuelingUiState,
                onValueChange = viewModel::updateUiState,
                onConfirmClick = { /*TODO*/},
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

@Composable
fun FuelingAddBody(
    fuelingUiState: FuelingUiState,
    onValueChange : (FuelingDetail)->Unit,
    onConfirmClick: ()->Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

        FuelingAddForm(details = fuelingUiState.details, onValueChange = onValueChange)

        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center ){
            Button(onClick = onConfirmClick, modifier.fillMaxWidth()) {
                Text(text = "Potvrď")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelingAddForm(
    details : FuelingDetail,
    onValueChange: (FuelingDetail) -> Unit
){
    Column {
        OutlinedTextField(
            value = details.quantity,
            label = { Text("Natankované množstvo") },
            onValueChange = { onValueChange(details.copy(quantity = it)) },
            leadingIcon = { Text("liters") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = details.total_price,
            label = {Text("Cena")},
            onValueChange = {onValueChange(details.copy(total_price = it))},
            leadingIcon = {Text(Currency.getInstance(Locale.getDefault()).symbol)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = details.odometter,
            label = {Text("Odometer")},
            onValueChange = {onValueChange(details.copy(odometter = it))},
            leadingIcon = {Text("km")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
        )
        DateTimeRow(details = details)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeRow(
    details: FuelingDetail,
    modifier: Modifier = Modifier
){
    var datePickerVisible by remember { mutableStateOf(false) }
    var timePickerVisible by remember { mutableStateOf(false) }


    Row (modifier = modifier){
        Column (modifier = Modifier
            .clickable(
                onClick = { datePickerVisible = true })
            .padding(5.dp)
        ) {
//            OutlinedTextField(
//                value = details.time.let { SimpleDateFormat("dd.MM.yyy").format(it) ?: "" },
//                onValueChange = {},
//                label = { Text("Dátum") },
//                readOnly = true,
//                enabled = false,
//            )
            OutlinedCard (

            ){
                Text(text = "Dátum", fontSize = 15.sp, modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                Text(text = details.time.let { SimpleDateFormat("dd.MM.yyy").format(it) ?: "" },
                    modifier=Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp,))
            }
        }

        Column (modifier = Modifier
            .clickable(onClick = { timePickerVisible = true })
            .padding(5.dp)
        ) {
            OutlinedCard (
            ){
                Text(text = "Čas", fontSize = 15.sp, modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                Text(text = details.time.let { SimpleDateFormat("HH:mm").format(it) ?: "" },
                    modifier=Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp,))
            }
        }
    }

    if(datePickerVisible){
        val dateState  = rememberDatePickerState( initialSelectedDateMillis = details.time?.time ?: null )
        Column {
            DatePickerDialog(
                dismissButton = {
                    Button(onClick = { datePickerVisible = false }) {
                        Text(text = "Zruš")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val date = dateState.selectedDateMillis?.let{Date(it)}
                        details.copy( time = date)
                        datePickerVisible = false
                    }) {
                        Text(text = "OK")
                    }
                },
                onDismissRequest = { datePickerVisible = false }
            ) {
                DatePicker(state = dateState)
            }
        }
    }
    if(timePickerVisible){
        val timeState = rememberTimePickerState( initialHour =  details.time?.hours ?: 12 )
        //TimePickerDialog()
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
                    FuelingAddBody(fuelingUiState = FuelingUiState(true,FuelingDetail(quantity = "50.4", total_price = "72.54", full_tank = true, fuel_type = "Natural95", odometter = "90123")),
                        onValueChange = {}, onConfirmClick = { /*TODO*/ },
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