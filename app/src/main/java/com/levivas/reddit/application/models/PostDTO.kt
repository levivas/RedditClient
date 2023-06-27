package com.levivas.reddit.application.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
class PostDTO(
    val title: String,
    @Json(name = "subreddit_name_prefixed") val subRedditName: String?,
    @Json(name = "created") val date: LocalDateTime?,
    val author: String?,
    val thumbnail: String?,
    val score: Int,
    @Json(name = "num_comments") val commentsSize: Int,
    @Json(name = "permalink") val link: String?
)

@JsonClass(generateAdapter = true)
class DataPostDTO(
    var kind: String,
    var data: DataChildrenDTO
)

@JsonClass(generateAdapter = true)
class DataChildrenDTO(
    var dist: String,
    var children: List<DataChildrenItemDTO>,
    val after: String?,
    val before: String?
)

@JsonClass(generateAdapter = true)
class DataChildrenItemDTO(
    var kind: String,
    var data: PostDTO
)
