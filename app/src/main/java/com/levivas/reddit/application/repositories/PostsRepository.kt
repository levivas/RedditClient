package com.levivas.reddit.application.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.application.network.calls.PostsCalls
import com.levivas.reddit.utils.PAGINATION_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PostsRepository @Inject constructor(private val postsCalls: PostsCalls) {

    fun getPostsFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Post>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PostPagingSource(postsCalls) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = PAGINATION_SIZE, enablePlaceholders = false)
    }
}
