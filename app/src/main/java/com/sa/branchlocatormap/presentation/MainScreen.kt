package com.sa.branchlocatormap.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sa.branchlocatormap.presentation.navigation.BottomNavItemScreen
import com.sa.branchlocatormap.presentation.navigation.Screen
import com.sa.branchlocatormap.presentation.viewModel.BranchSharedViewModel
import org.koin.androidx.compose.koinViewModel

val items = listOf(
    BottomNavItemScreen.Map,
    BottomNavItemScreen.Favourites,
    BottomNavItemScreen.History
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute in items.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {

                    items.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = null
                                )
                            },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItemScreen.Map.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(BottomNavItemScreen.Map.route) {
                MapsScreen(Modifier, navController)
            }

            composable(BottomNavItemScreen.Favourites.route) {
                val sharedViewModel: BranchSharedViewModel = koinViewModel()
                FavouritesScreen(
                    onBranchClick = { branch ->
                        sharedViewModel.selectBranch(branch)
                        navController.navigate(Screen.BRANCH_DETAIL)
                    }
                )
            }

            composable(BottomNavItemScreen.History.route) {
                HistoryScreen()
            }

            composable(Screen.BRANCH_DETAIL) {
                BranchDetailScreen(navController)
            }
        }
    }
}