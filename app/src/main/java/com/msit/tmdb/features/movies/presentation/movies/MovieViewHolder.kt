package com.msit.tmdb.features.movies.presentation.movies

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msit.tmdb.core.util.Constants
import com.msit.tmdb.databinding.MovieItemBinding
import com.msit.tmdb.features.movies.data.local.entity.Movie

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    init {

    }

    fun bind(movie: Movie) {
        binding.apply {
            item = movie
            executePendingBindings()
        }
    }
}