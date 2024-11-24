package cz.frantisekhlinka.amiright.frontauth.viewmodel

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.frantisekhlinka.amiright.backauth.repo.AuthRepo
import cz.frantisekhlinka.amiright.coredata.util.Event
import cz.frantisekhlinka.amiright.corefront.extensions.MutableEventFlow
import cz.frantisekhlinka.amiright.corefront.extensions.call
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class AuthViewModel(
    private val authRepo: AuthRepo,
    private val credentialManager: CredentialManager
) : ViewModel() {

    // in a more complex app, these events would probably be abstracted into a base ViewModel
    private val _errorEvent = MutableEventFlow<Unit>()
    val errorEvent: SharedFlow<Event<Unit>> = _errorEvent

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun authWithGoogle(activityContext: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            val request = authRepo.getCredentialRequestForAuthWithGoogle()
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = activityContext,
                )

                authRepo.authWithGoogleCredential(result.credential)

                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                if (e !is GetCredentialCancellationException) {
                    Log.e("AuthViewModel", "Authentication error: ", e)
                    _errorEvent.call()
                }
            }
        }
    }

    companion object {
        private const val EVENTS_REPLAY = 20
    }
}