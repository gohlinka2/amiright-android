package cz.frantisekhlinka.amiright.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.frantisekhlinka.amiright.coredata.navigation.NavigationRoute
import cz.frantisekhlinka.amiright.coreui.views.util.DummyCenteredText
import cz.frantisekhlinka.amiright.frontauth.ui.screen.AuthScreen
import cz.frantisekhlinka.amiright.frontcreatepost.ui.screen.CreatePostScreen
import cz.frantisekhlinka.amiright.fronthome.ui.navigationscaffold.HomeScreenScaffold
import cz.frantisekhlinka.amiright.fronthome.ui.screen.FeedScreen
import cz.frantisekhlinka.amiright.fronthome.ui.screen.MyPostsScreen
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
                homeNavHost = {
                    HomeNavHost(
                        homeNavController,
                        modifier = Modifier
                            .padding(it)
                            .consumeWindowInsets(it),
                        navigateToCreatePost = { rootNavController.navigate(NavigationRoute.CreatePost.route) }
                    )
                },
                navigateToTab = { route -> homeNavController.navigateToTab(route) }
            )
        }
        composable(NavigationRoute.CreatePost.route) {
            CreatePostScreen(
                navigateBack = {
                    rootNavController.popBackStack(
                        route = NavigationRoute.Home.GRAPH_ROUTE,
                        inclusive = false
                    )
                }
            )
        }
    }
}

/**
 * Nested navigation graph for the home screen tabs.
 */
@Composable
private fun HomeNavHost(
    homeNavController: NavHostController,
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = homeNavController,
        startDestination = NavigationRoute.Home.Feed.route,
        route = NavigationRoute.Home.GRAPH_ROUTE
    ) {
        composable(NavigationRoute.Home.Feed.route) {
            FeedScreen(navigateToCreatePost = navigateToCreatePost)
        }
        composable(NavigationRoute.Home.MyPosts.route) {
            MyPostsScreen(navigateToCreatePost = navigateToCreatePost)
        }
    }
}
