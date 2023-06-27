package com.levivas.reddit.features.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.levivas.reddit.R
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.databinding.ItemPostBinding
import com.levivas.reddit.extensions.setOnSafeClickListener
import com.levivas.reddit.utils.POST_TEMPLATE_TIME_FORMAT
import com.levivas.reddit.utils.openWithChromeTabs
import java.time.format.DateTimeFormatter

class PostsAdapter : PagingDataAdapter<Post, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PostViewHolder)?.bind(item = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(itemBinding)
    }

    class PostViewHolder(private val itemBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val dateTimeFormatter = DateTimeFormatter.ofPattern(POST_TEMPLATE_TIME_FORMAT)

        fun bind(item: Post?) {
            itemBinding.apply {
                ivThumbnail.load(item?.thumbnail) {
                    transformations(RoundedCornersTransformation(20f))
                    fallback(R.drawable.image_placeholder)
                    error(R.drawable.image_placeholder)
                }
                tvTitle.text = item?.title
                tvSubRedditName.text = item?.subRedditName
                tvScore.text = item?.score.toString()
                tvAuthor.text = item?.author
                tvCreatedAt.text = item?.date?.format(dateTimeFormatter)
                tvCommentsSize.text = item?.commentsSize.toString()
                root.setOnSafeClickListener {
                    it.context.openWithChromeTabs(item?.link)
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post) =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: Post, newItem: Post) =
                oldItem.link == newItem.link
        }
    }
}