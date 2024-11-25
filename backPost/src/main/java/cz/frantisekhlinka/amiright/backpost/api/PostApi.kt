package cz.frantisekhlinka.amiright.backpost.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.functions.FirebaseFunctions
import cz.frantisekhlinka.amiright.coreback.communication.DocumentSnapshotWrapper
import cz.frantisekhlinka.amiright.coreback.communication.parse
import cz.frantisekhlinka.amiright.coreback.communication.toFirestoreTimestamp
import cz.frantisekhlinka.amiright.coreback.communication.toLong
import cz.frantisekhlinka.amiright.coreback.communication.toModelFlow
import cz.frantisekhlinka.amiright.coredata.Post
import cz.frantisekhlinka.amiright.coredata.constants.CloudFunctionNames
import cz.frantisekhlinka.amiright.coredata.constants.CloudFunctionParams
import cz.frantisekhlinka.amiright.coredata.constants.FirestoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

internal class PostApi(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseFunctions: FirebaseFunctions,
) {

    /**
     * Gets the latest post that was updated after the given [lastPostTimestamp], or the latest post if
     * [lastPostTimestamp] is null.
     *
     * Returns null if there are no posts.
     */
    suspend fun getLatestPostAfter(lastPostTimestamp: Long?): Post? {
        val baseQuery = firebaseFirestore.collection(FirestoreKeys.POSTS)
            .orderBy(FirestoreKeys.Post.UPDATED_AT, Query.Direction.DESCENDING)
            .limit(1)
        val query = when {
            lastPostTimestamp != null -> baseQuery.whereLessThan(
                FirestoreKeys.Post.UPDATED_AT,
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
        firebaseFirestore.collection(FirestoreKeys.POSTS).document(postId).toModelFlow { asPost() }

    fun getPostsByAuthorUid(authorUid: String): Flow<List<Post>> =
        firebaseFirestore.collection(FirestoreKeys.POSTS)
            .whereEqualTo(FirestoreKeys.Post.AUTHOR_UID, authorUid)
            .orderBy(FirestoreKeys.Post.UPDATED_AT, Query.Direction.DESCENDING)
            .toModelFlow { asPost() }

    /**
     * Reacts to a post with the given [postId].
     * If [agree] is true, the user agrees with the post, otherwise they disagree.
     */
    suspend fun reactToPost(postId: String, agree: Boolean) {
        firebaseFunctions.getHttpsCallable(CloudFunctionNames.REACT_TO_POST).call(
            mapOf(
                CloudFunctionParams.ReactToPost.POST_ID to postId,
                CloudFunctionParams.ReactToPost.AGREE to agree
            )
        ).await()
    }

    /**
     * Creates a new post with the given [text].
     */
    suspend fun createPost(text: String) {
        firebaseFunctions.getHttpsCallable(CloudFunctionNames.NEW_POST).call(
            mapOf(CloudFunctionParams.NewPost.TEXT to text)
        ).await()
    }

    private fun DocumentSnapshotWrapper.asPost() = Post(
        id = id,
        text = getString(FirestoreKeys.Post.TEXT),
        authorUid = getString(FirestoreKeys.Post.AUTHOR_UID),
        createdAt = getTimestamp(FirestoreKeys.Post.CREATED_AT).toLong(),
        updatedAt = getTimestamp(FirestoreKeys.Post.UPDATED_AT).toLong(),
        agreeUids = getStringList(FirestoreKeys.Post.AGREE_UIDS),
        disagreeUids = getStringList(FirestoreKeys.Post.DISAGREE_UIDS)
    )
}