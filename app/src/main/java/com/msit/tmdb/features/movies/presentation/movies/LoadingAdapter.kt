package com.msit.tmdb.features.movies.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.msit.tmdb.databinding.LoadingItemBinding

class LoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingViewHolder>() {

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = LoadingViewHolder(
        LoadingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), retry
    )
}