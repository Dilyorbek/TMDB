package com.msit.tmdb.features.movies.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.msit.tmdb.databinding.MoviesFragmentBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@FlowPreview
class MoviesFragment : Fragment() {
    private val TAG = MoviesFragment::class.java.name

    val viewModel: MoviesViewModel by viewModel()

    lateinit var binding: MoviesFragmentBinding

    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        fetchMovies()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.fetchMovies().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

    }

    private fun setupViews() {
        binding.apply {
            viewModel = this@MoviesFragment.viewModel
            executePendingBindings()

            movies.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter { adapter.retry() },
                footer = LoadingAdapter { adapter.retry() }
            )
        }
    }
}