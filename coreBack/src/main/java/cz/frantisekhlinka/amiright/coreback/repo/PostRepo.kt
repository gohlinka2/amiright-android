package cz.frantisekhlinka.amiright.coreback.repo

import cz.frantisekhlinka.amiright.coredata.Post
import kotlinx.coroutines.flow.Flow

interface PostRepo {
    /**
     * Gets a post from the feed.
     * Posts where the current user is already involved will be skipped.
     */
    fun getFeedPost(nextPostKey: Long?): Flow<FeedPostPageData>

    /**
     * Reacts to a post with the given [postId].
     * If [agree] is true, the user agrees with the post, otherwise they disagree.
     */
    suspend fun reactToPost(postId: String, agree: Boolean)

    /**
     * Creates a new post with the given [text].
     */
    suspend fun createPost(text: String)
}

/**
 * Represents a page in the feed of posts.
 */
sealed class FeedPostPageData {
    /**
     * The user has reached the end of the feed.
     */
    data object NoMorePosts : FeedPostPageData()

    /**
     * Wraps a post in the feed and info on how to get the next post.
     */
    data class Loaded(
        val post: Post,

        /**
         * Pass this into [PostRepo.getFeedPost] to get the next post in the feed after this one.
         */
        val nextPostKey: Long
    ) : FeedPostPageData()
}