package com.levivas.reddit.application.models

import com.levivas.reddit.utils.POST_TEMPLATE_TIME_FORMAT
import java.time.format.DateTimeFormatter

class Post(
    val postId: String,
    val title: String,
    val subRedditName: String,
    val date: String,
    val author: String,
    val thumbnail: String?,
    val score: Int,
    val commentsSize: Int,
    val link: String
)

private val dateTimeFormatter = DateTimeFormatter.ofPattern(POST_TEMPLATE_TIME_FORMAT)

fun PostDTO.mapToPost(): Post {
    return Post(
        postId = this.postId,
        title = this.title,
        subRedditName = this.subRedditName.orEmpty(),
        date = this.date?.format(dateTimeFormatter).orEmpty(),
        author = this.author.orEmpty(),
        thumbnail = this.thumbnail,
        score = this.score,
        commentsSize = this.commentsSize,
        link = this.link.orEmpty()
    )
}
