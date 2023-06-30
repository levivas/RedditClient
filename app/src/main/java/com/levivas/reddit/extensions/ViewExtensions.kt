package com.levivas.reddit.extensions

import android.view.View
import coil.load
import coil.transform.RoundedCornersTransformation
import com.levivas.reddit.R
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.databinding.ItemPostBinding
import com.levivas.reddit.utils.IMAGE_CORNER_RADIUS

fun View.setOnSafeClickListener(listenerBlock: (View) -> Unit) =
    setOnClickListener(OnSafeClickListener(listenerBlock))

fun ItemPostBinding.showPost(post: Post) {
    ivThumbnail.load(post.thumbnail) {
        transformations(RoundedCornersTransformation(IMAGE_CORNER_RADIUS))
        fallback(R.drawable.image_placeholder)
        error(R.drawable.image_placeholder)
    }
    tvTitle.text = post.title
    tvSubRedditName.text = post.subRedditName
    tvScore.text = post.score.toString()
    tvAuthor.text = post.author
    tvCreatedAt.text = post.date.format(com.levivas.reddit.utils.dateTimeFormatter)
    tvCommentsSize.text = post.commentsSize.toString()
}
