package cz.frantisekhlinka.amiright.corefront.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<T>.stateInViewModel(
        initialValue: T,
        sharingStrategy: SharingStarted = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT),
    ): StateFlow<T> = stateIn(viewModelScope, sharingStrategy, initialValue)

    protected fun <T> Flow<T>.stateInViewModel(
        sharingStrategy: SharingStarted = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT),
    ): StateFlow<T?> = stateIn(viewModelScope, sharingStrategy, null)

    companion object {
        private const val STATE_IN_STOP_TIMEOUT = 5_000L
    }
}
