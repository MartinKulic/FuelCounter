package com.example.vapmzsem.ui.Route

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vapmzsem.R
import com.example.vapmzsem.data.Route
import com.example.vapmzsem.ui.AppViewModelProvider
import com.example.vapmzsem.ui.Fueling.FuelingItem
import com.example.vapmzsem.ui.Fueling.MyItemDetailDisplay
import com.example.vapmzsem.ui.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date
import java.util.Locale

object RouteScreenDestination : NavigationDestination{
    override val route: String = "routes"
    override val titleRes: Int = R.string.cesty

}

@Composable
fun RouteScreen(
    onNewRouteClick : () -> Unit = {},
    onItemClicked : (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: RouteViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState by viewModel.routeScreenUiState.collectAsState()

    Column(modifier = modifier) {
        Row(){
            Button(onClick = onNewRouteClick) {
                Text(text = "Nová Cesta")
            }
        }
        RouteList(onItemClicked = onItemClicked, routeList = uiState.routesList)
    }
}

@Composable
fun RouteList(
    onItemClicked: (Int) -> Unit,
    routeList : List<RouteAsUi>,
    modifier: Modifier = Modifier
){
    if(routeList.isEmpty()){
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Zatial žiadne Cesty :(", style = MaterialTheme.typography.titleLarge)
    }
    else{
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = routeList,
                key = { route -> route.id }
            ) { route ->
                RouteItem(
                    item = route,
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onItemClicked(route.id) }
                )
            }
        }

    }
}

@Composable
fun RouteItem(item: RouteAsUi, modifier: Modifier) {
    Row(modifier = Modifier.fillMaxWidth()){
        ElevatedCard (
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ){
            Row(modifier = Modifier.fillMaxWidth()){
                Text(text= item.title, fontSize = 15.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(item.start_time))
                Text(text = SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(item.finish_time.timeInMillis - item.start_time.timeInMillis))
            }
            MyItemDetailDisplay("km", item.distance)

        }
    }
}

@Preview
@Composable
fun RouteListPreview(){
    MaterialTheme{
        Surface {
            val list = listOf(
                Route(id_R = 0, id_F = null, title = "", distance = 40f, start_time =  Date(), finish_time =  Date(), finish_odometer = 200, start_point = "Kanianka", finish_point = "Zilina" ).toUi(),
                Route(id_R = 1, id_F = null, title = "Výlet niekde", distance = 40f, start_time =  Date(), finish_time =  Date(), finish_odometer = 200, start_point = "Kanianka", finish_point = "Zilina" ).toUi()
            )
            RouteList(onItemClicked = {}, routeList = list)
        }
    }
}