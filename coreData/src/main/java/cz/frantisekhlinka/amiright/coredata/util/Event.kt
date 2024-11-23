package cz.frantisekhlinka.amiright.coredata.util

/**
 * Used as a wrapper for data that represents an event (should be handled only once).
 */
open class Event<out T>(private val content: T) {

    var consumed = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun consume(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }
}
