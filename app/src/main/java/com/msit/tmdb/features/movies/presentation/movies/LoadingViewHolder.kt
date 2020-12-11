package com.msit.tmdb.features.movies.presentation.movies

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.msit.tmdb.databinding.LoadingItemBinding

class LoadingViewHolder(private val binding: LoadingItemBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retry.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                errorMessage.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            errorMessage.isVisible = loadState !is LoadState.Loading
            retry.isVisible = loadState !is LoadState.Loading
        }
    }
}