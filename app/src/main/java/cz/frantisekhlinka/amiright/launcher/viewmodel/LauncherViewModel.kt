package cz.frantisekhlinka.amiright.launcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Determines what parts of the app to show the user - based on state such as authentication.
 */
class LauncherViewModel(
    private val authRepo: IAuthStateRepo
) : ViewModel() {

    val isAuthenticated: StateFlow<Boolean?> = authRepo.getCurrentUidFlow().map { it != null }.distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}