package cz.frantisekhlinka.amiright.backpost.api

import com.google.firebase.firestore.FirebaseFirestore
import cz.frantisekhlinka.amiright.coreback.communication.DocumentSnapshotWrapper
import cz.frantisekhlinka.amiright.coreback.communication.toModelFlow
import cz.frantisekhlinka.amiright.coredata.Post
import cz.frantisekhlinka.amiright.coredata.constants.FirebaseKeys
import kotlinx.coroutines.flow.Flow

internal class PostApi(
    private val firebaseFirestore: FirebaseFirestore
) {

    /**
     * Gets a flow of updates of a post with the given [postId].
     */
    fun getPostById(postId: String): Flow<Post?> =
        firebaseFirestore.collection(FirebaseKeys.POSTS).document(postId).toModelFlow { asPost() }

    private fun DocumentSnapshotWrapper.asPost() = Post(
        id = id,
        text = getString(FirebaseKeys.Post.TEXT),
        authorUid = getString(FirebaseKeys.Post.AUTHOR_UID),
        createdAt = getTimestamp(FirebaseKeys.Post.CREATED_AT).seconds,
        updatedAt = getTimestamp(FirebaseKeys.Post.UPDATED_AT).seconds,
        agreeUids = getStringList(FirebaseKeys.Post.AGREE_UIDS),
        disagreeUids = getStringList(FirebaseKeys.Post.DISAGREE_UIDS)
    )
}