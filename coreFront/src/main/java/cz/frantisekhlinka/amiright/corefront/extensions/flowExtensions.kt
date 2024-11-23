package cz.frantisekhlinka.amiright.corefront.extensions

import cz.frantisekhlinka.amiright.coredata.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Helper function to make calling (Unit) [Event] easier
 */
suspend fun MutableSharedFlow<Event<Unit>>.call() = emit(Event(Unit))

/**
 * Helper function to make calling (Unit) [Event] easier
 */
fun MutableSharedFlow<Event<Unit>>.tryCall() = tryEmit(Event(Unit))

/**
 * Calls the given [listener] with the content of any new [Event] that hasn't been consumed yet.
 */
suspend fun <T> Flow<Event<T>>.consume(listener: suspend (T) -> Unit) {
    collectLatest { event ->
        event.consume()?.let {
            listener.invoke(it)
        }
    }
}