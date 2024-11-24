package cz.frantisekhlinka.amiright.backpost.repo

import cz.frantisekhlinka.amiright.backpost.api.PostApi
import cz.frantisekhlinka.amiright.coreback.repo.FeedPostPageData
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

internal class PostRepoImpl(
    private val postApi: PostApi,
    private val authStateRepo: IAuthStateRepo,
) : PostRepo {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedPost(nextPostKey: Long?): Flow<FeedPostPageData> = flow {
        val currentUid = authStateRepo.getCurrentUid()
        val initialPost = postApi.getLatestPostAfter(nextPostKey)

        if (initialPost == null) {
            // reached the end of posts
            emit(FeedPostPageData.NoMorePosts)
        } else {
            if (initialPost.involvedUids.contains(currentUid)) {
                // the user is already involved in this post, so go to the next one
                emitAll(getFeedPost(initialPost.updatedAt))
            } else {
                val postFlow = postApi.getPostById(initialPost.id).flatMapLatest { post ->
                    if (post == null) {
                        // if post is null, which means it stopped existing, get the next post after it
                        getFeedPost(initialPost.updatedAt)
                    } else {
                        // else just propagate the post and the timestamp
                        flowOf(FeedPostPageData.Loaded(post, initialPost.updatedAt))
                    }
                }
                emitAll(postFlow)
            }
        }
    }

    override suspend fun reactToPost(postId: String, agree: Boolean) = postApi.reactToPost(postId, agree)
}
