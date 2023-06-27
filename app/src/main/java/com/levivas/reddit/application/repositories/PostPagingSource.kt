package com.levivas.reddit.application.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.application.models.mapToPost
import com.levivas.reddit.application.network.calls.PostsCalls
import okio.IOException
import retrofit2.HttpException

class PostPagingSource(private val postsCalls: PostsCalls) : PagingSource<String, Post>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return try {
            val response = when (params.key) {
                null -> postsCalls.getInitialPosts(params.loadSize)
                else -> postsCalls.getRestPosts(params.key.orEmpty(), params.loadSize)
            }
            LoadResult.Page(
                response.body()!!.data.children.map { it.data.mapToPost() },
                prevKey = response.body()?.data?.before,
                nextKey = response.body()?.data?.after
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        TODO("Not yet implemented")
    }
}