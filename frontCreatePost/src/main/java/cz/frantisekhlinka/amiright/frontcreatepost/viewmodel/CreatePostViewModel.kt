package cz.frantisekhlinka.amiright.frontcreatepost.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import cz.frantisekhlinka.amiright.coredata.util.Event
import cz.frantisekhlinka.amiright.corefront.extensions.MutableEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class CreatePostViewModel(
    private val postRepo: PostRepo
) : ViewModel() {

    private val _text = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)

    // in a more complex app, these events would probably be abstracted into a base ViewModel
    private val _saveResultEvent = MutableEventFlow<Boolean>()
    val saveResultEvent = _saveResultEvent.asSharedFlow()

    val state: StateFlow<CreatePostState> = combine(_text, isLoading) { text, loading ->
        if (loading) {
            CreatePostState.Loading
        } else {
            CreatePostState.Idle(text)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CreatePostState.Idle(""))

    /**
     * Updates the current editor text.
     * The maximum length is [MAX_POST_LENGTH].
     */
    fun updateText(text: String) {
        _text.value = text.take(MAX_POST_LENGTH)
    }

    /**
     * Sends the post to the backend.
     */
    fun send() {
        val currentState = state.value
        if (currentState is CreatePostState.Idle && currentState.isValid) {
            viewModelScope.launch {
                isLoading.value = true
                try {
                    postRepo.createPost(currentState.text)
                    _saveResultEvent.emit(Event(true))
                } catch (e: Exception) {
                    Log.e("CreatePostViewModel", "Error creating post", e)
                    _saveResultEvent.emit(Event(false))
                } finally {
                    isLoading.value = false
                }
            }
        }
    }

    companion object {
        private const val MAX_POST_LENGTH = 100
    }
}

sealed class CreatePostState {
    data object Loading : CreatePostState()
    data class Idle(
        val text: String
    ) : CreatePostState() {
        val isValid: Boolean
            get() = text.isNotBlank()
    }
}