package com.levivas.reddit.features.postDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.levivas.reddit.R
import com.levivas.reddit.databinding.ItemPostBinding
import com.levivas.reddit.extensions.showPost
import com.levivas.reddit.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailFragment : Fragment(R.layout.item_post) {
    private val binding by viewBinding(ItemPostBinding::bind)
    private val viewModel by viewModels<PostDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyInsets()
        observePostsFlow()
    }

    private fun applyInsets() {
        binding.root.applyInsetter { type(statusBars = true) { padding(top = true) } }
    }

    private fun observePostsFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.post.collectLatest {
                    it?.let { item -> binding.showPost(item) }
                }
            }
        }
    }
}