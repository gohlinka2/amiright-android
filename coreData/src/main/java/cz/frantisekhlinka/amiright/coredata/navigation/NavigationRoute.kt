package cz.frantisekhlinka.amiright.coredata.navigation

sealed class NavigationRoute {
    abstract val route: String

    data object Splash: NavigationRoute() {
        override val route: String = "splash"
    }

    data object Auth: NavigationRoute() {
        override val route: String = "auth"
    }

    data object Home: NavigationRoute() {
        override val route: String = "home"
    }
}