package com.example.vapmzsem.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.vapmzsem.R
import com.example.vapmzsem.TabTopAppBar
import com.example.vapmzsem.ui.Fueling.FuelingDetailDestination
import com.example.vapmzsem.ui.Fueling.FuelingScreen
import com.example.vapmzsem.ui.Home.HomeScreen
import com.example.vapmzsem.ui.Home.HomeScreenDestination
import com.example.vapmzsem.ui.Route.RouteScreen
import com.example.vapmzsem.ui.Route.RouteScreenDestination
import com.example.vapmzsem.ui.Statistics.StatisticScreen
import com.example.vapmzsem.ui.Statistics.StatisticsScreenDestination
import com.example.vapmzsem.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object MainScreenDestination : NavigationDestination{
    override val route: String = "main_screen"
    override val titleRes: Int = R.string.vamz_sem_prac

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    fuelingItemClicked : (Int) -> Unit,
    fuelingNewClicked : () -> Unit,
    routeItemClicked: (Int) -> Unit,
    routeNewClicked: () -> Unit
){
//    var tabIndex by rememberSaveable {
//        mutableStateOf(1)
//    }
    val titles = listOf(FuelingDetailDestination.titleRes, HomeScreenDestination.titleRes, RouteScreenDestination.titleRes, StatisticsScreenDestination.titleRes)
    var pagerState = rememberPagerState (pageCount = {4}, initialPage = 1)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = { TabTopAppBar(pagerState.currentPage, onClick = {coroutineScope.launch { pagerState.scrollToPage(it) }}, titles = titles) }) {
            innerPadding ->
        HorizontalPager(state = pagerState) {
                page -> when(page) {
        0->FuelingScreen(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxSize(),
            onItemClicked = fuelingItemClicked,
            onNewFuelingClick = fuelingNewClicked)

        1->HomeScreen(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxSize(),
            onFuelingClicked = fuelingItemClicked,
            onRouteClicked = routeItemClicked
        )
        2->RouteScreen(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxSize(),
            onNewRouteClick = routeNewClicked,
            onItemClicked = routeItemClicked
        )
        3-> StatisticScreen(modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = innerPadding.calculateTopPadding()
            ).fillMaxSize())

    }
    }


    }
}