package cz.frantisekhlinka.amiright.fronthome.ui.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import cz.frantisekhlinka.amiright.coredata.navigation.NavigationRoute
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.IndicatorColor

/**
 * The home screen of the app. This composable is responsible solely for displaying the bottom navigation
 * and owning the home nav host. The actual navigation logic between specific tabs is handled by the
 * navigation logic on the top level, in the app module.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenScaffold(
    backStackEntryState: State<NavBackStackEntry?>,
    homeNavHost: @Composable () -> Unit,
    navigateToTab: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by backStackEntryState
                val currentDestination = backStackEntry?.destination

                homeNavBarItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(item.label)) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = { navigateToTab(item.route) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = IndicatorColor
                        )
                    )
                }
            }
        }
    ) {
        homeNavHost()
    }
}

private data class HomeNavBarItem(
    val route: String,
    val icon: ImageVector,
    @StringRes
    val label: Int
)

private val homeNavBarItems = listOf(
    HomeNavBarItem(
        route = NavigationRoute.Home.Feed.route,
        icon = Icons.Rounded.Home,
        label = R.string.feed
    ),
    HomeNavBarItem(
        route = NavigationRoute.Home.MyPosts.route,
        icon = Icons.Rounded.AccountCircle,
        label = R.string.my_posts
    )
)