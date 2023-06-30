package com.levivas.reddit.features.posts

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.levivas.reddit.R
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.application.repositories.PostsRepository
import com.levivas.reddit.utils.eventDispatchers.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val POST_ID_PARAM = "POST_ID_PARAM"
const val SUBREDDIT_NAME_PREFIXED = "SUBREDDIT_NAME_PREFIXED"

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val repository: PostsRepository
) : ViewModel() {

    fun fetchPosts(): Flow<PagingData<Post>> = repository.getPostsFlow()

    fun onOpenPostDetailClick(postId: String, subredditNamePrefixed: String) =
        goOpenPostDetail(postId, subredditNamePrefixed)

    private fun goOpenPostDetail(postId: String, subredditNamePrefixed: String) {
        navigationDispatcher.emit {
            it.navigate(
                R.id.fragmentPostDetail,
                bundleOf(POST_ID_PARAM to postId, SUBREDDIT_NAME_PREFIXED to subredditNamePrefixed)
            )
        }
    }
}
