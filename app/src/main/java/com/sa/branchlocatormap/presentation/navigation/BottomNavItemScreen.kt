package com.sa.branchlocatormap.presentation.navigation

import com.sa.branchlocatormap.R

import androidx.annotation.DrawableRes

/**
 * Represents items in the Bottom Navigation Bar.
 *
 * This sealed class defines all available bottom navigation screens
 * in a type-safe way, preventing invalid routes from being used.
 *
 * Each item contains:
 * - route: Navigation destination identifier
 * - icon: Drawable resource for UI icon
 * - title: Display label shown in the bottom bar
 */
sealed class BottomNavItemScreen(
    val route: String,
    @DrawableRes val icon: Int,
    val title: String
) {

    /**
     * Map screen showing branches on a map view.
     */
    data object Map : BottomNavItemScreen(
        route = "map_screen",
        icon = R.drawable.ic_maps,
        title = "Map"
    )

    /**
     * Screen showing user's favourite branches.
     */
    data object Favourites : BottomNavItemScreen(
        route = "favourites_screen",
        icon = R.drawable.ic_favorite,
        title = "Favourites"
    )

    /**
     * Screen showing navigation/history-related data.
     */
    data object More : BottomNavItemScreen(
        route = "more_screen",
        icon = R.drawable.ic_more,
        title = "More"
    )
}

/**
 * Centralized navigation routes used outside the bottom navigation system.
 *
 * Keeping routes in one place helps avoid:
 * - typos in string routes
 * - inconsistent navigation paths
 * - hard-to-track bugs in NavHost setup
 */
object Screen {

    /**
     * Route for the branch detail screen.
     *
     * Typically used when navigating from map or list items
     * to a detailed view of a selected branch.
     */
    const val BRANCH_DETAIL = "branch_detail_screen"
}

object MoreNavHost {
    const val HELP_SUPPORT = "help_support_screen"
    const val CONTACT_US = "contact_us_screen"
    const val FEEDBACK = "feedback_screen"

    const val COMPANY_INFO = "company_info_screen"
    const val PRIVACY_POLICY = "privacy_policy_screen"
    const val TERMS = "terms_screen"
}

