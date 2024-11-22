package cz.frantisekhlinka.amiright.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.frantisekhlinka.amiright.coredata.navigation.NavigationRoute
import cz.frantisekhlinka.amiright.frontauth.ui.screen.AuthScreen

@Composable
fun AmirightNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Auth.route
    ) {
        composable(NavigationRoute.Auth.route) {
            AuthScreen()
        }
    }
}
