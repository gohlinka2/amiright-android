package cz.frantisekhlinka.amiright.launcher.viewmodel

import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.corefront.extensions.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

/**
 * Determines what parts of the app to show the user - based on state such as authentication.
 */
class LauncherViewModel(
    private val authRepo: IAuthStateRepo
) : BaseViewModel() {

    val isAuthenticated: StateFlow<Boolean?> =
        authRepo.getCurrentUidFlow().map { it != null }.stateInViewModel()
}