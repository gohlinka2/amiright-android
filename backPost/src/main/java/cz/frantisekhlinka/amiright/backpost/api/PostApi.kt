package cz.frantisekhlinka.amiright.backpost.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import cz.frantisekhlinka.amiright.coreback.communication.DocumentSnapshotWrapper
import cz.frantisekhlinka.amiright.coreback.communication.parse
import cz.frantisekhlinka.amiright.coreback.communication.toFirestoreTimestamp
import cz.frantisekhlinka.amiright.coreback.communication.toLong
import cz.frantisekhlinka.amiright.coreback.communication.toModelFlow
import cz.frantisekhlinka.amiright.coredata.Post
import cz.frantisekhlinka.amiright.coredata.constants.FirebaseKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

internal class PostApi(
    private val firebaseFirestore: FirebaseFirestore
) {

    /**
     * Gets the latest post that was updated after the given [lastPostTimestamp], or the latest post if
     * [lastPostTimestamp] is null.
     *
     * Returns null if there are no posts.
     */
    suspend fun getLatestPostAfter(lastPostTimestamp: Long?): Post? {
        val baseQuery = firebaseFirestore.collection(FirebaseKeys.POSTS)
            .orderBy(FirebaseKeys.Post.UPDATED_AT, Query.Direction.DESCENDING)
            .limit(1)
        val query = when {
            lastPostTimestamp != null -> baseQuery.whereLessThan(
                FirebaseKeys.Post.UPDATED_AT,
                lastPostTimestamp.toFirestoreTimestamp()
            )

            else -> baseQuery
        }
        return query.get().await().documents.firstNotNullOfOrNull { it.parse { asPost() } }
    }

    /**
     * Gets a flow of updates of a post with the given [postId].
     */
    fun getPostById(postId: String): Flow<Post?> =
        firebaseFirestore.collection(FirebaseKeys.POSTS).document(postId).toModelFlow { asPost() }

    private fun DocumentSnapshotWrapper.asPost() = Post(
        id = id,
        text = getString(FirebaseKeys.Post.TEXT),
        authorUid = getString(FirebaseKeys.Post.AUTHOR_UID),
        createdAt = getTimestamp(FirebaseKeys.Post.CREATED_AT).toLong(),
        updatedAt = getTimestamp(FirebaseKeys.Post.UPDATED_AT).toLong(),
        agreeUids = getStringList(FirebaseKeys.Post.AGREE_UIDS),
        disagreeUids = getStringList(FirebaseKeys.Post.DISAGREE_UIDS)
    )
}