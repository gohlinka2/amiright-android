package cz.frantisekhlinka.amiright.coreback.communication

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import cz.frantisekhlinka.amiright.coreback.exceptions.FirestoreParseException
import java.util.Date

/**
 * Represents a firestore document or its part that can be used to retrieve its values. Any firestore document parsing in the app should be done via this class.
 */
sealed class FirestoreDocumentParsable {
    protected abstract operator fun get(key: String): Any?

    /* Map wrapper */
    @Suppress("UNCHECKED_CAST")
    fun getMapOrNull(key: String): DocDataMapWrapper? = (this[key] as? Map<String, Any?>)?.let { DocDataMapWrapper(it) }
    fun getMap(key: String): DocDataMapWrapper = getMapOrNull(key) ?: throw FirestoreParseException()

    /* Primitives */
    fun getInt(key: String): Int = getIntOrNull(key) ?: throw FirestoreParseException()
    fun getIntOrNull(key: String): Int? = (this[key] as? Long ?: this[key] as? Int)?.toInt()
    fun getFloat(key: String): Float = getFloatOrNull(key) ?: throw FirestoreParseException()
    fun getFloatOrNull(key: String): Float? = (this[key] as? Double ?: this[key] as? Long ?: this[key] as? Int)?.toFloat()
    fun getDoubleOrNull(key: String): Double? = (this[key] as? Double ?: this[key] as? Long ?: this[key] as? Int)?.toDouble()
    fun getBooleanOrNull(key: String): Boolean? = this[key] as? Boolean
    fun getBoolean(key: String): Boolean = getBooleanOrNull(key) ?: throw FirestoreParseException()
    fun getString(key: String): String = getStringOrNull(key) ?: throw FirestoreParseException()
    fun getStringOrNull(key: String): String? = this[key] as? String

    /* Lists */
    @Suppress("UNCHECKED_CAST")
    fun getListOfMapsOrNull(key: String): List<DocDataMapWrapper>? = (this[key] as? List<Any>)?.map { (it as? Map<String, Any?>) ?: return null }?.map { DocDataMapWrapper(it) }
    fun getListOfMaps(key: String): List<DocDataMapWrapper> = getListOfMapsOrNull(key) ?: throw FirestoreParseException()

    fun getStringList(key: String): List<String> = getStringListOrNull(key) ?: throw FirestoreParseException()

    @Suppress("UNCHECKED_CAST")
    fun getStringListOrNull(key: String): List<String>? = this[key] as? List<String>

    @Suppress("UNCHECKED_CAST")
    fun getStringListOrEmpty(key: String): List<String> = getStringListOrNull(key) ?: emptyList()

    @Suppress("UNCHECKED_CAST")
    fun getIntList(key: String): List<Int> {
        val asLong = this[key] as? List<Long> ?: throw FirestoreParseException()
        return asLong.map { it.toInt() }
    }

    /* Firebase */
    fun getTimestampOrNull(key: String): Timestamp? = this[key] as? Timestamp
    fun getTimestamp(key: String): Timestamp = getTimestampOrNull(key) ?: throw FirestoreParseException()
}

/**
 * Wraps a [DocumentSnapshot] for parsing. The [snap] is intentionally private to not allow getting and casting values directly, which could lead to crashes.
 */
data class DocumentSnapshotWrapper(private val snap: DocumentSnapshot) : FirestoreDocumentParsable() {
    val id
        get() = snap.id

    val metadata
        get() = snap.metadata

    override fun get(key: String): Any? = snap[key]
}

/**
 * Wraps a map retrieved from [DocumentSnapshotWrapper.getMap] for parsing. The [map] is intentionally private to not allow getting and casting values directly, which could lead to crashes.
 */
data class DocDataMapWrapper(private val map: Map<String, Any?>) : FirestoreDocumentParsable() {
    /**
     * Returns a read-only [Set] of all keys in the [map].
     */
    val keys: Set<String>
        get() = map.keys

    override fun get(key: String): Any? = map[key]
}

fun Timestamp.toLong() = toDate().time

private fun Long.toFirestoreTimestamp() = Timestamp(Date(this))
