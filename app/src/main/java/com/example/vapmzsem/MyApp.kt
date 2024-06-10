package com.example.vapmzsem


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vapmzsem.ui.navigation.MyNavHost


@Composable
fun MyApp(navController: NavHostController = rememberNavController()){
    MyNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabTopAppBar(
    selectedIndex : Int = 0,
    onClick : (Int) -> Unit,
    titles : List<Int>
){
    Column(modifier = Modifier
        .fillMaxWidth()
        ) {

        ScrollableTabRow(selectedTabIndex = selectedIndex) {
            titles.forEachIndexed { index, title ->
                Tab(text = { Text(stringResource(id = title)) },
                    selected = selectedIndex == index,
                    onClick = {onClick(index)},
                    icon = {
                        when(index){
                            0-> Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = null
                            )
                            1-> Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = null
                            )
                            2-> Icon(
                                imageVector = Icons.Filled.Place,
                                contentDescription = null
                            )
                            3-> Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
){
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        })
}