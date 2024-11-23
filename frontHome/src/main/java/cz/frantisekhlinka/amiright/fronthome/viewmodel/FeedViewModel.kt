package cz.frantisekhlinka.amiright.fronthome.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FeedViewModel : ViewModel() {
    private val _state = MutableStateFlow<FeedUIModel>(FeedUIModel.Loading)
    val state = _state.asStateFlow()

    init {
        // TODO: remove this dummy data
        _state.value = FeedUIModel.Post(
            postStatement = "Pineapple on pizza is a crime against humanity.",
            voteData = VoteData(
                negativeVotes = 39,
                positiveVotes = 61,
            ),
        )
    }
}

internal sealed class FeedUIModel {
    data object Loading : FeedUIModel()
    data object NoPosts : FeedUIModel()
    data class Post(
        val postStatement: String,
        val voteData: VoteData?
    ) : FeedUIModel()
}

internal data class VoteData(
    val negativeVotes: Int,
    val positiveVotes: Int
)