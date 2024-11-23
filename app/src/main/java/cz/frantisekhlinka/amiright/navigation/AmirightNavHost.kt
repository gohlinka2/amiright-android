package cz.frantisekhlinka.amiright.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.frantisekhlinka.amiright.coredata.navigation.NavigationRoute
import cz.frantisekhlinka.amiright.frontauth.ui.screen.AuthScreen

@Composable
fun AmirightNavHost(
    isAuthenticated: Boolean?,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = when (isAuthenticated) {
            null -> NavigationRoute.Splash.route
            true -> NavigationRoute.Home.route
            false -> NavigationRoute.Auth.route
        }
    ) {
        composable(NavigationRoute.Splash.route) {
            SplashScreen()
        }
        composable(NavigationRoute.Auth.route) {
            AuthScreen()
        }
        composable(NavigationRoute.Home.route) {
            // TODO replace
            Scaffold {
                Box(
                    Modifier.padding(it).consumeWindowInsets(it).fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Home")
                }
            }
        }
    }
}

@Composable
private fun SplashScreen() {
    Scaffold {
        Box(
            Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
