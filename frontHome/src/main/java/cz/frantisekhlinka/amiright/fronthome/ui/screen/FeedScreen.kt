package cz.frantisekhlinka.amiright.fronthome.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.AMIRIGHTTheme
import cz.frantisekhlinka.amiright.coreui.theme.HintText
import cz.frantisekhlinka.amiright.fronthome.ui.view.FeedHeader
import cz.frantisekhlinka.amiright.fronthome.ui.view.ResultsCard
import cz.frantisekhlinka.amiright.fronthome.ui.view.VoteIndicators
import cz.frantisekhlinka.amiright.fronthome.ui.view.VoteResults
import cz.frantisekhlinka.amiright.fronthome.ui.view.VoteTouchTargets
import cz.frantisekhlinka.amiright.fronthome.viewmodel.FeedUIModel
import cz.frantisekhlinka.amiright.fronthome.viewmodel.FeedViewModel
import cz.frantisekhlinka.amiright.fronthome.viewmodel.VoteData
import org.koin.androidx.compose.koinViewModel

/**
 * Shows one post with a bold statement at a time.
 * The user can vote on the statement by typing on the left or right side of the screen.
 * After the user votes, the results are shown and they can move to the next post.
 */
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit = {},
) {
    val viewModel: FeedViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    FeedScreenStateless(
        model = state,
        modifier = modifier,
        onVote = viewModel::vote,
        onNextClicked = viewModel::nextPost,
        onAddButtonClick = navigateToCreatePost
    )
}

@Composable
internal fun FeedScreenStateless(
    model: FeedUIModel,
    modifier: Modifier = Modifier,
    onAddButtonClick: () -> Unit = {},
    onVote: (Boolean) -> Unit = {},
    onNextClicked: () -> Unit = {},
) {
    Surface {
        Box(modifier.fillMaxSize()) {
            when (model) {
                FeedUIModel.Loading -> FeedLoading()
                FeedUIModel.NoPosts -> FeedNoPosts()
                is FeedUIModel.Post -> FeedPost(model, onVote, onNextClicked)
            }
            FeedHeader(
                onAddButtonClick = onAddButtonClick,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .safeContentPadding()
            )
        }
    }
}

@Composable
private fun FeedLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun FeedNoPosts(
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.no_more_posts_come_back_later),
            style = MaterialTheme.typography.displayMedium,
            color = HintText,
            modifier = Modifier.padding(horizontal = 67.dp)
        )
    }
}

@Composable
private fun FeedPost(
    state: FeedUIModel.Post,
    onVote: (Boolean) -> Unit = {},
    onNextClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
    ) {
        val voteData = state.voteData
        if (voteData == null) {
            VoteTouchTargets(onVote)
        }
        // vote results need to be included all the time because of the animation
        VoteResults(voteData)
        if (voteData == null) {
            VoteIndicators(
                Modifier
                    .align(Alignment.BottomCenter)
                    .safeContentPadding()
            )
        }
        Text(
            state.postStatement,
            style = MaterialTheme.typography.displayMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .padding(horizontal = 67.dp)
                .align(Alignment.Center)
        )
        if (voteData != null) {
            ResultsCard(
                voteData,
                onNextClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 32.dp)
            )
        }
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    AMIRIGHTTheme {
        FeedScreenStateless(
            model = FeedUIModel.Post(
                "Pineapple on pizza is a crime against humanity.",
                VoteData(
                    negativeVotes = 39,
                    positiveVotes = 61,
                ),
            )
        )
    }
}

@Preview
@Composable
private fun FeedLoadingPreview() {
    AMIRIGHTTheme {
        FeedLoading()
    }
}

@Preview
@Composable
private fun FeedNoPostsPreview() {
    AMIRIGHTTheme {
        FeedNoPosts()
    }
}
