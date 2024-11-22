package cz.frantisekhlinka.amiright.coredata.navigation

sealed class NavigationRoute {
    abstract val route: String

    data object Auth: NavigationRoute() {
        override val route: String = "auth"
    }
}