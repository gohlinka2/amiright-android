package cz.frantisekhlinka.amiright.fronthome.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.frantisekhlinka.amiright.coreui.R
import cz.frantisekhlinka.amiright.coreui.theme.HintText
import cz.frantisekhlinka.amiright.fronthome.ui.view.MyPostsHeader
import cz.frantisekhlinka.amiright.fronthome.ui.view.VoteResults
import cz.frantisekhlinka.amiright.fronthome.viewmodel.MyPostUIModel
import cz.frantisekhlinka.amiright.fronthome.viewmodel.MyPostsState
import cz.frantisekhlinka.amiright.fronthome.viewmodel.MyPostsViewModel
import cz.frantisekhlinka.amiright.fronthome.viewmodel.VoteData
import org.koin.androidx.compose.koinViewModel

/**
 * Shows a list of posts made by the current user.
 */
@Composable
fun MyPostsScreen(
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit
) {
    val viewModel: MyPostsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPostsScreenStateless(
        state = state,
        modifier = modifier,
        navigateToCreatePost = navigateToCreatePost
    )
}

@Composable
fun MyPostsScreenStateless(
    state: MyPostsState,
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                MyPostsHeader(onAddButtonClick = navigateToCreatePost)
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (state is MyPostsState.Data) {
                items(state.myPosts, key = { it.postId }) { post ->
                    PostCard(
                        post,
                        Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // empty states
        if (state is MyPostsState.Loading) {
            CircularProgressIndicator()
        } else if (state is MyPostsState.Data && state.myPosts.isEmpty()) {
            Text(
                stringResource(id = R.string.you_have_no_posts_yet),
                style = MaterialTheme.typography.displayMedium.copy(color = HintText),
                modifier = Modifier.padding(horizontal = 67.dp)
            )
        }
    }
}

@Composable
fun PostCard(
    postUIModel: MyPostUIModel,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            VoteResults(VoteData(postUIModel.negativeVotes, postUIModel.positiveVotes))
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = postUIModel.postStatement,
                    style = MaterialTheme.typography.titleSmall.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val textStyle = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = postUIModel.negativeVotes.toString(),
                        style = textStyle
                    )
                    Text(
                        text = postUIModel.positiveVotes.toString(),
                        style = textStyle
                    )
                }
            }
        }
    }
}