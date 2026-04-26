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
/**
 * List of bottom navigation items used in the application's main navigation bar.
 *
 * This list defines the screens that appear in the bottom navigation and their order:
 * - Map screen
 * - Favourites screen
 * - History screen
 *
 * Each item is represented using [BottomNavItemScreen], which contains
 * route, icon, and title information for navigation rendering.
 */
val items = listOf(
    BottomNavItemScreen.Map,
    BottomNavItemScreen.Favourites,
    BottomNavItemScreen.History
)

/**
 * Main entry composable for the application UI.
 *
 * This composable sets up:
 * - Navigation controller
 * - Bottom navigation bar
 * - Navigation graph (NavHost)
 * - Screen routing logic
 */
@Composable
fun MainScreen() {

    /**
     * Navigation controller responsible for handling screen transitions.
     */
    val navController = rememberNavController()

    /**
     * Tracks the currently selected bottom navigation index.
     */
    var selectedIndex by remember { mutableIntStateOf(0) }

    /**
     * Observes the current back stack entry from the NavController.
     * Used to determine the active route.
     */
    val backStackEntry by navController.currentBackStackEntryAsState()

    /**
     * Current active route extracted from the navigation destination.
     */
    val currentRoute = backStackEntry?.destination?.route

    /**
     * Determines whether the bottom navigation bar should be visible
     * based on whether the current route belongs to bottom navigation items.
     */
    val showBottomBar = currentRoute in items.map { it.route }

    Scaffold(
        bottomBar = {

            /**
             * Bottom navigation bar displayed conditionally.
             */
            if (showBottomBar) {
                NavigationBar {

                    /**
                     * Iterates through bottom navigation items and renders each tab.
                     */
                    items.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = selectedIndex == index,

                            /**
                             * Handles navigation when a bottom tab is clicked.
                             */
                            onClick = {
                                selectedIndex = index

                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },

                            /**
                             * Icon displayed for each bottom navigation item.
                             */
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = null
                                )
                            },

                            /**
                             * Text label for the bottom navigation item.
                             */
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { padding ->

        /**
         * Navigation host defining all app routes and their corresponding screens.
         */
        NavHost(
            navController = navController,
            startDestination = BottomNavItemScreen.Map.route,
            modifier = Modifier.padding(padding)
        ) {

            /**
             * Map screen destination.
             */
            composable(BottomNavItemScreen.Map.route) {
                MapsScreen(Modifier, navController)
            }

            /**
             * Favourites screen destination.
             *
             * Uses a shared ViewModel to pass selected branch data
             * to the detail screen.
             */
            composable(BottomNavItemScreen.Favourites.route) {

                val sharedViewModel: BranchSharedViewModel = koinViewModel()

                FavouritesScreen(
                    onBranchClick = { branch ->
                        sharedViewModel.selectBranch(branch)
                        navController.navigate(Screen.BRANCH_DETAIL)
                    }
                )
            }

            /**
             * History screen destination.
             */
            composable(BottomNavItemScreen.History.route) {
                HistoryScreen()
            }

            /**
             * Branch detail screen destination.
             */
            composable(Screen.BRANCH_DETAIL) {
                BranchDetailScreen(navController)
            }
        }
    }
}