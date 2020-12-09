package com.msit.tmdb.features.movies.presentation.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msit.tmdb.core.util.Constants
import com.msit.tmdb.databinding.MovieItemBinding
import com.msit.tmdb.features.numberTrivia.domain.entity.Movie


class MovieAdapter() : PagingDataAdapter<Movie, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var items = mutableListOf<Movie>()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bindTo(items[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    fun setItems(items: List<Movie>) {
        this.items.clear()
        this.items.addAll(items);
        notifyDataSetChanged()
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(movie: Movie) {
            binding.movie = movie
            Glide
                .with(binding.root.context)
                .load(Constants.IMAGE_URL + movie.posterPath)
                .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(binding.poster)
            binding.executePendingBindings()
        }
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