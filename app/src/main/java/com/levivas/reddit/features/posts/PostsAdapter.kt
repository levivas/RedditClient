package com.levivas.reddit.features.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.levivas.reddit.application.models.Post
import com.levivas.reddit.databinding.ItemPostBinding
import com.levivas.reddit.extensions.setOnSafeClickListener
import com.levivas.reddit.extensions.showPost

class PostsAdapter constructor(val onOpenPostDetailClick: (String, String) -> Unit) :
    PagingDataAdapter<Post, RecyclerView.ViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PostViewHolder)?.bind(nullablePost = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(itemBinding)
    }

    inner class PostViewHolder(private val itemBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(nullablePost: Post?) {
            nullablePost?.let { post ->
                itemBinding.apply {
                    showPost(post)
                    root.setOnSafeClickListener {
                        onOpenPostDetailClick(post.postId, nullablePost.subRedditName)
//                    it.context.openWithChromeTabs(item?.link)
                    }
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