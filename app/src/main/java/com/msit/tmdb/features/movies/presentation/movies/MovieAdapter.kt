package com.msit.tmdb.features.movies.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.msit.tmdb.databinding.MovieItemBinding
import com.msit.tmdb.features.movies.data.local.entity.Movie


class MovieAdapter() : PagingDataAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
}