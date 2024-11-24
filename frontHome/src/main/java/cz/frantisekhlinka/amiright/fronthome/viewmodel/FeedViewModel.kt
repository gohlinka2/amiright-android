package cz.frantisekhlinka.amiright.fronthome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.frantisekhlinka.amiright.coreback.repo.FeedPostPageData
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class FeedViewModel(
    private val authStateRepo: IAuthStateRepo,
    private val postRepo: PostRepo
) : ViewModel() {

    private val lastPostKey = MutableStateFlow<Long?>(null)

    // The user's vote on the current post, or null if they haven't voted yet. We cache and display the user's vote
    // immediately after voting and send the vote to the server in the background, so the user doesn't have to wait.
    private val localVote = MutableStateFlow<Boolean?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _postData: StateFlow<FeedPostPageData?> = lastPostKey.flatMapLatest {
        flow {
            // emit null first, so that the UI can show a loading state
            emit(null)
            emitAll(postRepo.getFeedPost(it))
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val state = combine(_postData, localVote) { postPageData, agreed ->
        when (postPageData) {
            is FeedPostPageData.Loaded -> {
                val post = postPageData.post
                FeedUIModel.Post(
                    postStatement = post.text,
                    voteData = agreed?.let {
                        val negativeVotes = post.disagreeUids.size
                        val positiveVotes = post.agreeUids.size

                        // if the user's vote has not reached the server yet, add their local vote manually
                        val userVoteReachedServer = post.reactorUids.contains(authStateRepo.getCurrentUid())
                        if (!userVoteReachedServer) {
                            VoteData(
                                negativeVotes = if (!agreed) negativeVotes + 1 else negativeVotes,
                                positiveVotes = if (agreed) positiveVotes + 1 else positiveVotes,
                            )
                        } else {
                            VoteData(
                                negativeVotes = negativeVotes,
                                positiveVotes = positiveVotes
                            )
                        }
                    }
                )
            }

            FeedPostPageData.NoMorePosts -> FeedUIModel.NoPosts
            null -> FeedUIModel.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, FeedUIModel.Loading)

    /**
     * Votes on the current post.
     * @param agree true if the user agrees with the post, false if they disagree.
     */
    fun vote(agree: Boolean) {
        val data = _postData.value
        if (data is FeedPostPageData.Loaded) {
            localVote.value = agree
            // send the vote to the server in the background
            viewModelScope.launch {
                // TODO: call backend to vote
            }
        }
    }

    /**
     * Moves to the next post in the feed.
     */
    fun nextPost() {
        val data = _postData.value
        if (data is FeedPostPageData.Loaded) {
            lastPostKey.value = data.nextPostKey
            localVote.value = null
        }
    }
}

internal sealed class FeedUIModel {
    data object Loading : FeedUIModel()
    data object NoPosts : FeedUIModel()
    data class Post(
        val postStatement: String,

        /**
         * The number of positive and negative votes on the post.
         * If null, the user must vote first to see the votes.
         */
        val voteData: VoteData?
    ) : FeedUIModel()
}

internal data class VoteData(
    val negativeVotes: Int,
    val positiveVotes: Int
)