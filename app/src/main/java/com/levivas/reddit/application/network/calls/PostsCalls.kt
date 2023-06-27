package com.levivas.reddit.application.network.calls

import com.levivas.reddit.application.models.DataPostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostsCalls {
    @GET("top.json")
    suspend fun getInitialPosts(@Query("limit") limit: Int): Response<DataPostDTO>

    @GET("top.json")
    suspend fun getRestPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): Response<DataPostDTO>
}
