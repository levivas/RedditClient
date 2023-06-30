package com.levivas.reddit.application.network.calls

import com.levivas.reddit.application.models.DataPostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsCalls {

    @GET("top.json")
    suspend fun getRestPosts(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null
    ): Response<DataPostDTO>


    @GET("/{subredditNamePrefixed}/api/info.json")
    suspend fun getPostDetail(
        @Path(value = "subredditNamePrefixed", encoded = true) subredditNamePrefixed: String,
        @Query("id") postId: String
    ): Response<DataPostDTO>
}
