package cz.frantisekhlinka.amiright.coreback.communication

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.snapshots
import cz.frantisekhlinka.amiright.coreback.exceptions.FirestoreParseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Converts this Firestore document into a flow of updates of that document.
 * A snapshot listener will be attached once a terminal operator (such as collect) is applied to the flow.
 * The listener will get removed if a firestore exception is thrown or if the parent coroutine that
 * applied the terminal operator is cancelled.
 *
 * Parses the document into a [T] using the [transform] function. See [DocumentSnapshot.parse] for more details.
 */
fun <T> DocumentReference.toModelFlow(transform: DocumentSnapshotWrapper.() -> T): Flow<T?> {
    return snapshots().map {
        it.parse { transform() }
    }
}

/**
 * Parses the [DocumentSnapshot] into a [T] using the [transform] function.
 * If the document doesn't exist or the parsing fails, returns null.
 */
fun <T> DocumentSnapshot.parse(transform: DocumentSnapshotWrapper.() -> T): T? {
    return if (exists()) {
        try {
            DocumentSnapshotWrapper(this).transform()
        } catch (ex: FirestoreParseException) {
            Log.e("DocumentSnapshot.parse", ex.toString())
            null
        }
    } else {
        null
    }
}