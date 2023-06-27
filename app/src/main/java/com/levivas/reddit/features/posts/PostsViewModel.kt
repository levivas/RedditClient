package com.levivas.reddit.features.posts

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.application.repositories.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostsRepository) : ViewModel() {

    fun fetchPosts(): Flow<PagingData<Post>> = repository.getPostsFlow()
}
