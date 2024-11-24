package cz.frantisekhlinka.amiright.corefront.extensions

import cz.frantisekhlinka.amiright.coredata.util.Event
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * A convenience for creating a [MutableSharedFlow] of [Event]s, suitable for delivering one-time events, for
 * example for UI events like navigation or showing a snackbar. The flow has a buffer - it assumes that events
 * that are further in the past become less relevant for the UI (f.e. navigation events usually have only 1-2 relevant
 * events before moving to a different screen). The older events are dropped when the buffer is full, see [bufferSize].
 *
 * @param bufferSize The buffer size - if the buffer is full, the oldest event will be dropped. The flow will also
 * replay the last [bufferSize] events to new subscribers. Default is 20.
 */
@Suppress("FunctionName")
fun <T> MutableEventFlow(
    bufferSize: Int = 20,
): MutableSharedFlow<Event<T>> =
    MutableSharedFlow(replay = bufferSize, onBufferOverflow = BufferOverflow.DROP_OLDEST)

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