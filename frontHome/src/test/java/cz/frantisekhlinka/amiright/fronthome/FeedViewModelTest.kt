package cz.frantisekhlinka.amiright.fronthome

import cz.frantisekhlinka.amiright.coreback.repo.FeedPostPageData
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import cz.frantisekhlinka.amiright.coredata.Post
import cz.frantisekhlinka.amiright.coretest.MainDispatcherTestRule
import cz.frantisekhlinka.amiright.fronthome.viewmodel.FeedUIModel
import cz.frantisekhlinka.amiright.fronthome.viewmodel.FeedViewModel
import io.mockk.MockKAnnotations
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class FeedViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherTestRule()

    private val currentUid = "currentUid"
    private val altUid = "altUid"
    private val postIdPrefix = "post_"
    private val authStateRepo: IAuthStateRepo = object : IAuthStateRepo {
        override fun getCurrentUidFlow(): Flow<String?> = flowOf(getCurrentUid())
        override fun getCurrentUid() = currentUid
    }
    private val initialTimestamp = 10L

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun emits_loading_before_post_is_loaded() = runTest {
        val viewModel = FeedViewModel(authStateRepo, buildFakePostRepo())

        val stateValues = viewModel.state.take(2).toList()

        assert(stateValues[0] == FeedUIModel.Loading)
        assert(stateValues[1] is FeedUIModel.Post)
    }

    @Test
    fun does_not_show_votes_when_user_has_not_voted() = runTest {
        val viewModel = FeedViewModel(authStateRepo, buildFakePostRepo())

        val stateValues = viewModel.state.take(2).toList()

        assert(stateValues[1] is FeedUIModel.Post)
        assert((stateValues[1] as FeedUIModel.Post).voteData == null)
    }

    @Test
    fun shows_local_vote_when_user_has_voted() = runTest {
        val savesVoteToServer = false
        checkShowsVote(savesVoteToServer)
    }

    @Test
    fun does_not_show_local_vote_when_vote_has_reached_server() = runTest {
        val savesVoteToServer = true
        checkShowsVote(savesVoteToServer)
    }

    @Test
    fun moves_to_next_post_with_correct_key_and_clears_local_vote() = runTest {
        val repo = spyk(
            buildFakePostRepo(
                updatePostWhileBeingViewed = true,
                savesVoteToServer = false,
            )
        )
        val viewModel = FeedViewModel(
            authStateRepo,
            repo
        )

        // wait for the first post (the updated initial post)
        viewModel.state.firstOrNull { it is FeedUIModel.Post && it.postStatement == "${postIdPrefix}0_updated" }

        viewModel.vote(true)

        val votedPost = viewModel.state.firstOrNull { it is FeedUIModel.Post && it.postStatement == "${postIdPrefix}0_updated" && it.voteData != null }

        // assert vote is in place
        assert(votedPost is FeedUIModel.Post && votedPost.voteData?.positiveVotes == 1)

        viewModel.nextPost()

        yield()

        // verify that the getFeedPost was called with the correct key (not the updated one)
        verify { repo.getFeedPost(initialTimestamp) }

        // wait for the second post
        val afterState =
            viewModel.state.firstOrNull { it is FeedUIModel.Post && it.postStatement == "${postIdPrefix}1_updated" }

        // assert the state after
        assert(afterState is FeedUIModel.Post && afterState.voteData == null)
    }

    private suspend fun checkShowsVote(savesVoteToServer: Boolean) {
        val viewModel = FeedViewModel(authStateRepo, buildFakePostRepo(savesVoteToServer = savesVoteToServer))

        // wait for the post to load
        viewModel.state.firstOrNull { it is FeedUIModel.Post }

        viewModel.vote(true)

        val updatedState = viewModel.state.firstOrNull { it is FeedUIModel.Post && it.voteData != null }
        assert(updatedState is FeedUIModel.Post && updatedState.voteData?.positiveVotes == 1)
    }

    private fun buildFakePostRepo(
        // true if we should simulate successful vote saving to the server
        savesVoteToServer: Boolean = true,
        // true if we should emit 2 emissions of the same post, simulating post update
        updatePostWhileBeingViewed: Boolean = false,
        // true if we should simulate post deletion, in which case we move to the next post
        deletePostWhileBeingViewed: Boolean = false,
    ): PostRepo = object : PostRepo {
        val currentUserServerReactions = MutableStateFlow(emptyMap<String, Boolean>())

        val posts = listOf(
            buildPost(0),
            buildPost(1),
        )

        override fun getFeedPost(nextPostKey: Long?): Flow<FeedPostPageData> {
            val emissions: List<FeedPostPageData> = buildList {
                val initialPost = findPost(nextPostKey)
                if (initialPost == null) {
                    add(FeedPostPageData.NoMorePosts)
                    return@buildList
                }
                add(FeedPostPageData.Loaded(initialPost, initialPost.updatedAt))

                // simulate post update (e.g. by another user)
                if (updatePostWhileBeingViewed) {
                    val updatedInitialPost =
                        initialPost.copy(updatedAt = initialPost.updatedAt + 1, text = "${initialPost.id}_updated")
                    add(FeedPostPageData.Loaded(updatedInitialPost, initialPost.updatedAt))
                }

                // simulate post deletion, in which case we move to the next post
                if (deletePostWhileBeingViewed) {
                    val nextPost = findPost(initialPost.updatedAt)
                    if (nextPost == null) {
                        add(FeedPostPageData.NoMorePosts)
                    } else {
                        add(FeedPostPageData.Loaded(nextPost, nextPost.updatedAt))
                    }
                }
            }
            return emissions.asFlow()
        }

        private fun buildPost(index: Int): Post {
            val id = "${postIdPrefix}$index"
            return Post(
                id = id,
                text = "${id}_initial",
                agreeUids = emptyList(),
                disagreeUids = emptyList(),
                authorUid = altUid,
                createdAt = initialTimestamp,
                updatedAt = initialTimestamp - index,
            )
        }

        private fun findPost(nextPostKey: Long?): Post? {
            return posts
                .sortedByDescending { it.updatedAt }
                .firstOrNull { post -> nextPostKey?.let { post.updatedAt < nextPostKey } ?: true }
                ?.let {
                    it.copy(
                        agreeUids = when {
                            currentUserServerReactions.value[it.id] == true -> it.agreeUids + currentUid
                            else -> it.agreeUids
                        },
                        disagreeUids = when {
                            currentUserServerReactions.value[it.id] == false -> it.disagreeUids + currentUid
                            else -> it.disagreeUids
                        },
                    )
                }
        }

        override suspend fun reactToPost(postId: String, agree: Boolean) {
            if (savesVoteToServer) {
                currentUserServerReactions.value += (postId to agree)
            }
        }

        // not under test
        override suspend fun createPost(text: String) = throw UnsupportedOperationException()
        override fun getMyPosts(): Flow<List<Post>> = throw UnsupportedOperationException()
    }
}