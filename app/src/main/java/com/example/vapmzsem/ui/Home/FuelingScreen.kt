package com.example.vapmzsem.ui.Home

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vapmzsem.data.Fueling
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun FuelingScreen(
    onNewFuelingClick : () -> Unit = {},
    onItemClicked : (Fueling) -> Unit = {},
    itemList: List<Fueling>,
    modifier: Modifier = Modifier
){
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
                Text(text = "Nové Tankovanie")
            }
        }
        Column (modifier = modifier.padding(start = 20.dp, end = 20.dp)){
            if (itemList.isEmpty()) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Zatial žiadne tankovania :(", style = MaterialTheme.typography.titleLarge)
            }
            else{
                FuelingList(
                    itemList = itemList,
                    onItemClicked = onItemClicked
                );
            }

        }}
}

@Composable
fun FuelingList(
    itemList: List<Fueling>,
    onItemClicked : (Fueling) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
//        for (fueling in itemList){
//            item (key = fueling.id_F) {
//                FuelingItem(item = fueling, modifier = modifier.padding(8.dp).clickable { onItemClicked(fueling) })
//            }
//        }

        items(
            items = itemList,
            key = { fueling -> fueling.id_F }
        ) { fueling ->
            FuelingItem(
                item = fueling,
                modifier = modifier
                    .padding(8.dp)
                    .clickable { onItemClicked(fueling) }
            )
        }
//        items(items = itemList, key = {it.id_F}) {
//            item -> FuelingItem(
//                item = item,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable { onItemClicked(item) }
//            )
//        }

    }
}

@Composable
fun FuelingItem(
    item : Fueling,
    modifier: Modifier
) {
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ){
        Column {
            val formated = SimpleDateFormat("dd.MM.yyyy HH:mm").format(item.time)
            Text(text = "Cas " + formated)
            Text(text = "Cena " + item.total_price)
            Text(text = "Natankovane Monžstvo " + item.quantity)
        }
    }
}

@Preview
@Composable
fun FuelingScreenPreviewEmplty(){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FuelingScreen(
            itemList = listOf()
        )
    }
}
@Preview
@Composable
fun FuelingScreenPreviewSomeItems(){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        FuelingScreen(
            itemList = listOf(
                Fueling(id_F = 0, quantity = 50.5f, total_price = 60.99f, full_tank = true, fuel_type = null, fueling_Station = null, time = Date(), odometter = null ),
                Fueling(id_F = 1, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 2, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 3, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 4, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 5, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 6, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 7, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 8, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 9, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 10, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 11, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 12, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 13, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 14, quantity = 42.87f, total_price = 50.12f),
                Fueling(id_F = 15, quantity = 60.0f, total_price = 100f),
                Fueling(id_F = 16, quantity = 45.99f, total_price = 62.71f, fuel_type = "Natural95", full_tank = true, fueling_Station = "Rajec začiaton Shell", odometter = 92015)
            )
        )
    }

}