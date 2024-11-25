package cz.frantisekhlinka.amiright.backpost

import cz.frantisekhlinka.amiright.backpost.api.PostApi
import cz.frantisekhlinka.amiright.backpost.repo.PostRepoImpl
import cz.frantisekhlinka.amiright.coreback.repo.FeedPostPageData
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.coredata.Post
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class PostRepoTest {

    @MockK
    lateinit var postApi: PostApi

    private val currentUid = "currentUid"
    private val altUid = "altUid"
    private val postIdPrefix = "post_"
    private val authStateRepo: IAuthStateRepo = object : IAuthStateRepo {
        override fun getCurrentUidFlow(): Flow<String?> = flowOf(getCurrentUid())
        override fun getCurrentUid() = currentUid
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun getFeedPost_returns_noMorePosts_when_initialPost_is_null() = runTest {
        coEvery { postApi.getLatestPostAfter(any()) } returns null

        val postRepo = PostRepoImpl(postApi, authStateRepo)

        val result = postRepo.getFeedPost(null).first()

        assert(result is FeedPostPageData.NoMorePosts)
    }

    @Test
    fun getFeedPost_emits_post_updates_when_user_is_not_involved() = runTest {
        var postIndex = 0
        coEvery { postApi.getLatestPostAfter(any()) } answers {
            // return a different post each time, with timestamps decreasing by 1
            fakePost(
                updated = (firstArg<Long?>()?.let { it - 1 } ?: 10),
                postId = "${postIdPrefix}${postIndex++}"
            )
        }
        mockGetPostByIdWithUpdates(listOf(10, 4))

        val postRepo = PostRepoImpl(postApi, authStateRepo)

        val results = postRepo.getFeedPost(null).toList()

        assert(results.size == 2)
        // assert that it emits only the 2 updates of the first post
        assert(results.all { it is FeedPostPageData.Loaded && it.post.id == "${postIdPrefix}0" })
        val secondResult = results[1] as FeedPostPageData.Loaded
        // The key of the next post to load should be the timestamp of the initial state of the currently displayed
        // post (not the new updated value).
        assertEquals(10L, secondResult.nextPostKey)
    }

    @Test
    fun getFeedPost_moves_to_next_post_if_user_is_already_involved() = runTest {
        var postIndex = 0
        coEvery { postApi.getLatestPostAfter(any()) } answers {
            // return a different post each time, with timestamps decreasing by 1
            // first post is by the current user, second post is by another user
            fakePost(
                updated = (firstArg<Long?>()?.let { it - 1 } ?: 10),
                author = if (postIndex == 0) currentUid else altUid,
                postId = "${postIdPrefix}${postIndex++}",
            )
        }
        mockGetPostByIdWithUpdates(listOf(10, 4), idMatcher = "${postIdPrefix}0")
        mockGetPostByIdWithUpdates(listOf(9, 4), idMatcher = "${postIdPrefix}1")

        val postRepo = PostRepoImpl(postApi, authStateRepo)
        val results = postRepo.getFeedPost(null).toList()

        assert(results.size == 2)
        // assert that it emits only the 2 updates of the second post
        assert(results.all { it is FeedPostPageData.Loaded && it.post.id == "${postIdPrefix}1" })
        val secondResult = results[1] as FeedPostPageData.Loaded
        // The key of the next post to load should be the timestamp of the initial state of the currently displayed
        // post (not the new updated value).
        assertEquals(9L, secondResult.nextPostKey)
    }

    @Test
    fun getFeedPost_moves_to_next_post_if_post_gets_deleted() = runTest {
        var postIndex = 0
        coEvery { postApi.getLatestPostAfter(any()) } answers {
            // return a different post each time, with timestamps decreasing by 1
            fakePost(
                updated = (firstArg<Long?>()?.let { it - 1 } ?: 10),
                postId = "${postIdPrefix}${postIndex++}"
            )
        }
        every { postApi.getPostById("${postIdPrefix}0") } answers {
            flowOf(
                // return the post, then delete it
                fakePost(updated = 10, postId = firstArg()),
                null
            )
        }
        mockGetPostByIdWithUpdates(listOf(9, 4), idMatcher = "${postIdPrefix}1")

        val postRepo = PostRepoImpl(postApi, authStateRepo)

        val results = postRepo.getFeedPost(null).toList()

        // first it should emit 1 update of the first post, then move on to the next one and emit its 2 updates
        assert(results.size == 3)
        assert(results[0] is FeedPostPageData.Loaded && (results[0] as FeedPostPageData.Loaded).post.id == "${postIdPrefix}0")
        assert(results.drop(1).all { it is FeedPostPageData.Loaded && it.post.id == "${postIdPrefix}1" })
        // The key of the next post to load should be the timestamp of the initial state of the second,
        // currently displayed post (and it should not be its new updated timestamp - 4).
        assert((results[2] as FeedPostPageData.Loaded).nextPostKey == 9L)
    }

    private fun mockGetPostByIdWithUpdates(updatedAtSequence: List<Long>, idMatcher: String? = null) {
        every { postApi.getPostById(idMatcher ?: any()) } answers {
            updatedAtSequence.map {
                fakePost(updated = it, postId = firstArg())
            }.asFlow()
        }
    }

    private fun fakePost(postId: String = "${postIdPrefix}0", updated: Long = 0, author: String = altUid): Post =
        Post(
            id = postId,
            text = "text",
            authorUid = author,
            createdAt = 0,
            updatedAt = updated,
            agreeUids = emptyList(),
            disagreeUids = emptyList()
        )
}
