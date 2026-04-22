package com.sa.branchlocatormap.presentation.navigation

import com.sa.branchlocatormap.R

sealed class BottomNavItemScreen(val route: String, val icon: Int, val title: String) {

    data object Map : BottomNavItemScreen(
        route = "map_screen",
        icon = R.drawable.ic_maps,
        title = "Map"
    )

    data object Favourites : BottomNavItemScreen(
        route = "favourites_screen",
        icon = R.drawable.ic_favorite,
        title = "Favourites"
    )

    data object History : BottomNavItemScreen(
        route = "history_screen",
        icon = R.drawable.ic_history,
        title = "History"
    )
}

object Screen {
    const val BRANCH_DETAIL = "branch_detail_screen"
}