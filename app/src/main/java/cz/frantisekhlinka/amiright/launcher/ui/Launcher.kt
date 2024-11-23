package cz.frantisekhlinka.amiright.launcher.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import cz.frantisekhlinka.amiright.launcher.viewmodel.LauncherViewModel
import cz.frantisekhlinka.amiright.navigation.AmirightNavHost
import org.koin.androidx.compose.koinViewModel

@Composable
fun Launcher() {
    val navController = rememberNavController()
    val viewModel: LauncherViewModel = koinViewModel()
    val isAuthenticated by viewModel.isAuthenticated.collectAsStateWithLifecycle()
    AmirightNavHost(isAuthenticated, navController)
}