package com.example.vapmzsem.ui.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.navigation.NavigationDestination
import com.example.vapmzsem.ui.theme.AppTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
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
    onEditClick : (Int) -> Unit,
    navigateBack : ()->Unit,
    onNavigateUp: () -> Unit = navigateBack,
    modifier: Modifier = Modifier,
    viewModel: FuelingDetailViewModel  = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState = viewModel.uiState.collectAsState()
    Scaffold (
        topBar = {
            MyTopAppBar(title = stringResource(FuelingDetailDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = { FuelingDetailBottomBar()}
    ) {
        innerPadding ->
            FuelingDetailBody(details = uiState.value, modifier= Modifier.fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(),
                    start = (15.dp),
                    end = (15.dp)
                )
            )

    }
}
@Composable
fun FuelingDetailBottomBar()
{
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom){
        OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(0.5f)) {
            Text(text= "Vymazať")
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth() ) {
            Text(text = "Upraviť")
        }
    }
}
@Composable
fun FuelingDetailBody(
    details : FuelingAsUi,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        val cenaZaL = ((details.total_price.toFloatOrNull()?:1f)/(details.quantity.toFloatOrNull()?:1f))
        DetailItem(title = "Cena", value = details.total_price, unit = Currency.getInstance(Locale.getDefault()).symbol)
        DetailItem(title = "Množstvo", value = details.quantity)
        DetailItem(title = "Cena za liter", value = DecimalFormat(
            "##.0000", DecimalFormatSymbols(
                Locale.getDefault()
            )).format(cenaZaL),
            unit = "${Currency.getInstance(Locale.getDefault()).symbol}/l")
        DetailItem(title = "Odometer", value = details.odometter, unit = "km")
        DetailItem(title = "Plná nádrž", value = if (details.full_tank) "Áno" else "Nie")
        DetailItem(title = "Dátum a čas", value = SimpleDateFormat("EEE dd.MM.yyyy 'o' HH:mm:ss", Locale.getDefault()).format(details.time.timeInMillis))
        DetailItem(title = "Pumpa", value = details.fueling_Station)
        DetailItem(title = "Typ paliva", value = details.fuel_type)

    }
}

@Composable
fun DetailItem(
    title : String,
    value : String,
    unit : String = ""
){
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "${title}: ", fontWeight = FontWeight.Bold)
        Text(text = "$value $unit")
    }
}

@Preview
@Composable
fun FuelingAddScreenPreview(){
    AppTheme {
        Surface {
            FuelingDetailBody(
                modifier = Modifier.fillMaxSize(),
                details = FuelingAsUi(quantity = "36.45", total_price = "45.23", full_tank = false, fuel_type = "Natural95", fueling_Station = "Rajec Shell", time = Calendar.getInstance(), odometter = "74864")
            )
        }
    }
}