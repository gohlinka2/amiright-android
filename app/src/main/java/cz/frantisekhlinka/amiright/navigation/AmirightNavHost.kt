package cz.frantisekhlinka.amiright.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.frantisekhlinka.amiright.coredata.navigation.NavigationRoute
import cz.frantisekhlinka.amiright.coreui.views.util.DummyCenteredText
import cz.frantisekhlinka.amiright.frontauth.ui.screen.AuthScreen
import cz.frantisekhlinka.amiright.fronthome.ui.navigationscaffold.HomeScreenScaffold
import cz.frantisekhlinka.amiright.launcher.ui.SplashScreen

@Composable
fun AmirightNavHost(
    isAuthenticated: Boolean?,
    rootNavController: NavHostController,
    homeNavController: NavHostController,
) {
    NavHost(
        navController = rootNavController,
        startDestination = when (isAuthenticated) {
            null -> NavigationRoute.Splash.route
            true -> NavigationRoute.Home.GRAPH_ROUTE
            false -> NavigationRoute.Auth.route
        }
    ) {
        composable(NavigationRoute.Splash.route) {
            SplashScreen()
        }
        composable(NavigationRoute.Auth.route) {
            AuthScreen()
        }
        composable(NavigationRoute.Home.GRAPH_ROUTE) {
            HomeScreenScaffold(
                backStackEntryState = homeNavController.currentBackStackEntryAsState(),
                homeNavHost = { HomeNavHost(homeNavController) },
                navigateToTab = { route -> homeNavController.navigateToTab(route) }
            )
        }
    }
}

/**
 * Nested navigation graph for the home screen tabs.
 */
@Composable
private fun HomeNavHost(homeNavController: NavHostController) {
    NavHost(
        navController = homeNavController,
        startDestination = NavigationRoute.Home.Feed.route,
        route = NavigationRoute.Home.GRAPH_ROUTE
    ) {
        composable(NavigationRoute.Home.Feed.route) {
            DummyCenteredText("Feed")
        }
        composable(NavigationRoute.Home.MyPosts.route) {
            DummyCenteredText("My Posts")
        }
    }
}
