package com.levivas.reddit.application.repositories.postDetail

import com.levivas.reddit.application.models.Post

sealed class GetPostDetailMethodResult {
    data class Success(val post: Post?) : GetPostDetailMethodResult()
    object Error : GetPostDetailMethodResult()
}
