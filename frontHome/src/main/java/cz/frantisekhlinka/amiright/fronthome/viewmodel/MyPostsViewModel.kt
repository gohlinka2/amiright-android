package cz.frantisekhlinka.amiright.fronthome.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MyPostsViewModel(
    private val postRepo: PostRepo
) : ViewModel() {

    val state: StateFlow<MyPostsState> = postRepo.getMyPosts()
        .map { posts ->
            MyPostsState.Data(posts.map { post ->
                MyPostUIModel(
                    postId = post.id,
                    postStatement = post.text,
                    negativeVotes = post.disagreeUids.size,
                    positiveVotes = post.agreeUids.size,
                )
            })
        }.stateIn(viewModelScope, SharingStarted.Eagerly, MyPostsState.Loading)
}

sealed class MyPostsState {
    data object Loading : MyPostsState()
    data class Data(val myPosts: List<MyPostUIModel>) : MyPostsState()
}

data class MyPostUIModel(
    val postId: String,
    val postStatement: String,
    val negativeVotes: Int,
    val positiveVotes: Int,
)