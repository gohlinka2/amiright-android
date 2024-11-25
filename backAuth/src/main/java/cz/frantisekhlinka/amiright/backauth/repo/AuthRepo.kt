package cz.frantisekhlinka.amiright.backauth.repo

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.coredata.GoogleWebClientId
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Exposes functionality to authenticate the user or retrieve the current auth state.
 *
 * This is purposefully not internal, because we expose the whole [AuthRepo] to the frontAuth module using a
 * direct dependency. This is so that the auth UI module can see the authWithX methods, while the rest of the app
 * only gets the [IAuthStateRepo] interface.
 */
class AuthRepo internal constructor(
    private val firebaseAuth: FirebaseAuth,
    private val clientId: GoogleWebClientId,
    private val credentialManager: CredentialManager
) : IAuthStateRepo {

    override fun getCurrentUidFlow(): Flow<String?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener {
            launch { send(firebaseAuth.currentUser?.uid) }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }.distinctUntilChanged()

    override fun getCurrentUid(): String? = firebaseAuth.currentUser?.uid

    /**
     * Returns a [GetCredentialRequest] to be passed to the credential manager in the UI layer
     * to request a Google ID token credential. This credential can then be passed in to
     * [authWithGoogleCredential] to authenticate the user with Firebase.
     */
    fun getCredentialRequestForAuthWithGoogle(): GetCredentialRequest {
        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(clientId.value).build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return request
    }

    /**
     * Authenticates the user with Firebase using the provided Google ID token credential.
     */
    suspend fun authWithGoogleCredential(credential: Credential) {
        try {
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                firebaseAuth.signInWithCredential(authCredential).await()
            } else {
                throw IllegalStateException("Unexpected type of credential: $credential")
            }
        } catch (e: Throwable) {
            // clear the state so that the user can try again if the authentication failed
            firebaseAuth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            throw e
        }
    }
}