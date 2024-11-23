package cz.frantisekhlinka.amiright.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/**
 * Navigates to the given route, treating it as a navigation to a tab.
 */
fun NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(
            this@navigateToTab.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}