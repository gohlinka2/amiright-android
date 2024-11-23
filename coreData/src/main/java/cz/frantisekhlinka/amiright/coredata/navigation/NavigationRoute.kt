package cz.frantisekhlinka.amiright.coredata.navigation

sealed class NavigationRoute {
    abstract val route: String

    data object Splash : NavigationRoute() {
        override val route: String = "splash"
    }

    data object Auth : NavigationRoute() {
        override val route: String = "auth"
    }

    sealed class Home : NavigationRoute() {
        companion object {
            const val GRAPH_ROUTE = "home"
        }

        data object Feed : Home() {
            override val route: String = "feed"
        }

        data object MyPosts : Home() {
            override val route: String = "my_posts"
        }
    }
}