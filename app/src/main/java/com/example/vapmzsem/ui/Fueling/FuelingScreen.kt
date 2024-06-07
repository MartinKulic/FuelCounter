package com.example.vapmzsem.ui.Fueling

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vapmzsem.data.Fueling
import com.example.vapmzsem.ui.AppViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.R
import com.example.vapmzsem.ui.navigation.NavigationDestination
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

object FuelingScreenDestination : NavigationDestination{
    override val route = "fueling"
    override val titleRes: Int = R.string.fueling_screen_title
}

@Composable
fun FuelingScreen(
    onNewFuelingClick : () -> Unit = {},
    onItemClicked : (Int) -> Unit = {},
    viewModel: FuelinViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val uiState by viewModel.fuelingScreenUiState.collectAsState()
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = onNewFuelingClick, modifier = modifier.fillMaxWidth()) {
                Text(text = "Nové Tankovanie")
            }
        }
        Column (modifier = modifier.padding(start = 20.dp, end = 20.dp)){
            FuelingList(
                itemList = uiState.itemList,
                onItemClicked = onItemClicked
            )
            }

        }
}

@Composable
fun FuelingList(
    itemList: List<FuelingAsUi>,
    onItemClicked : (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (itemList.isEmpty()) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Zatial žiadne tankovania :(", style = MaterialTheme.typography.titleLarge)
    }
    else{
        LazyColumn(
        modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = itemList,
            key = { fueling -> fueling.id }
        ) { fueling ->
            FuelingItem(
                item = fueling,
                modifier = modifier
                    .clickable { onItemClicked(fueling.id) }
                )
            }
        }
    }
}

@Composable
fun FuelingItem(
    item : FuelingAsUi,
    modifier: Modifier
) {
//    Card (
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        modifier = modifier.fillMaxWidth()
//    ){
//        Column {
//            val formated = SimpleDateFormat("dd.MM.yyyy HH:mm").format(item.time)
//            Text(text = "Cas " + formated)
//            Text(text = "Cena " + item.total_price)
//            Text(text = "Natankovane Monžstvo " + item.quantity)
//        }
//    }

    Row (modifier = Modifier.fillMaxWidth()){//medzera medzi Cartd .padding(top = 20.dp)
        ElevatedCard (modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MyItemDetailDisplay(
                    unit = SimpleDateFormat(
                        "dd.MM.yyyy",
                        Locale.getDefault()
                    ).format(item.time.timeInMillis),
                    value = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                        item.time.timeInMillis
                    ),
                    widthfill = 0.28f
                )
                MyItemDetailDisplay(
                    unit = "km", value = item.distance,
                    widthfill = 0.27f
                )
                MyItemDetailDisplay(
                    unit = "l", value = item.quantity,
                    widthfill = 0.3f
                )
                MyItemDetailDisplay(
                    unit = Currency.getInstance(Locale.getDefault()).symbol + "/l",
                    value = item.price_per_liter,
                    widthfill = 0.4f
                )
                MyItemDetailDisplay(
                    unit = Currency.getInstance(Locale.getDefault()).symbol,
                    value = (item.total_price),
                    widthfill = 0.8f
                )
            }
        }
    }
}
@Composable
fun MyItemDetailDisplay(
    unit: String,
    value: String,
    widthfill : Float = 1f
){
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(widthfill)) {
        Text(text = unit, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Preview
@Composable
fun FuelingItemPreview(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FuelingItem(item = Fueling(1, 31.24f, 51.85f, false, time = Date(), odometer = 999690).toUi(),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun FuelingScreenPreviewEmplty(){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FuelingList(
            itemList = listOf()
        )
    }
}
@Preview
@Composable
fun FuelingScreenPreviewSomeItems(){

            val someItems = listOf(
                Fueling(id_F = 0, quantity = 50.5f, total_price = 60.99f, full_tank = true, fuel_type = null, fueling_Station = null, time = Date(), odometer = null ).toUi(),
                Fueling(id_F = 1, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 2, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 3, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 4, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 5, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 6, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 7, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 8, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 11, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 12, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 13, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 14, quantity = 42.87f, total_price = 50.12f).toUi(),
                Fueling(id_F = 15, quantity = 60.0f, total_price = 100f).toUi(),
                Fueling(id_F = 16, quantity = 45.99f, total_price = 62.71f, fuel_type = "Natural95", full_tank = true, fueling_Station = "Rajec začiaton Shell", odometer = 92015).toUi()
            )
Surface(
modifier = Modifier.fillMaxSize(),
color = MaterialTheme.colorScheme.background
) {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = {  }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Nové Tankovanie")
            }
        }
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            FuelingList(
                itemList = someItems,
                onItemClicked = {}
            )
        }
    }
    }

}