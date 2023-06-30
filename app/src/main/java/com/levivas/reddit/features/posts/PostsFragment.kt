package com.levivas.reddit.features.posts

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.levivas.reddit.R
import com.levivas.reddit.databinding.FragmentPostsBinding
import com.levivas.reddit.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {
    private val binding by viewBinding(FragmentPostsBinding::bind)
    private val viewModel by viewModels<PostsViewModel>()
    lateinit var adapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyInsets()
        initAdapter()
        initRv()
        observePostsFlow()
    }

    private fun initRv() {
        binding.rvPosts.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecorator(
                    ContextCompat.getDrawable(context, R.drawable.divider)
                )
            )
            adapter = this@PostsFragment.adapter
        }
    }

    private fun initAdapter() {
        adapter = PostsAdapter { postId, subredditNamePrefixed -> viewModel.onOpenPostDetailClick(postId, subredditNamePrefixed)}
    }

    private fun applyInsets() {
        binding.rvPosts.applyInsetter { type(statusBars = true) { padding(top = true) } }
    }

    private fun observePostsFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchPosts().distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}
