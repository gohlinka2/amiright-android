package cz.frantisekhlinka.amiright.launcher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import cz.frantisekhlinka.amiright.launcher.viewmodel.LauncherViewModel
import cz.frantisekhlinka.amiright.navigation.AmirightNavHost
import org.koin.androidx.compose.koinViewModel

/**
 * The entry point of the app. Determines whether the user is authenticated and navigates to the
 * appropriate screen.
 */
@Composable
fun Launcher() {
    val rootNavController = rememberNavController()
    val homeNavController = rememberNavController()

    val viewModel: LauncherViewModel = koinViewModel()
    val isAuthenticated by viewModel.isAuthenticated.collectAsStateWithLifecycle()

    AmirightNavHost(isAuthenticated, rootNavController, homeNavController)
}