package com.levivas.reddit.features.postDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.application.repositories.PostsRepository
import com.levivas.reddit.application.repositories.postDetail.GetPostDetailMethodResult
import com.levivas.reddit.features.posts.POST_ID_PARAM
import com.levivas.reddit.features.posts.SUBREDDIT_NAME_PREFIXED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: PostsRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    val post = MutableStateFlow<Post?>(null)
    private val postId = savedStateHandle.get<String>(POST_ID_PARAM)!!
    private val subredditNamePrefixed = savedStateHandle.get<String>(SUBREDDIT_NAME_PREFIXED)!!

    init {
        getPostDetail(subredditNamePrefixed, postId)
    }

    private fun getPostDetail(subredditNamePrefixed: String, postId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            when (
                val result = repository.getPostDetail(subredditNamePrefixed, postId)
            ) {
                is GetPostDetailMethodResult.Success -> {
                    post.value = result.post
                }

                is GetPostDetailMethodResult.Error -> { // showError }
                }
            }
        }
}