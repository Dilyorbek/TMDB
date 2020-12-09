package com.msit.tmdb.features.movies.presentation.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msit.tmdb.core.util.EndlessScrollEventListener
import com.msit.tmdb.databinding.MoviesFragmentBinding
import kotlinx.android.synthetic.main.movies_fragment.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


@FlowPreview
class MoviesFragment : Fragment() {
    private val TAG = MoviesFragment::class.java.name

    val viewModel: MoviesViewModel by viewModel()

    lateinit var binding: MoviesFragmentBinding

    val adapter = MovieAdapter()

    init {

    }

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

        binding.apply {
            viewModel = this@MoviesFragment.viewModel
            executePendingBindings()

            movies.setHasFixedSize(true)
            val layoutManager = GridLayoutManager(requireContext(), 2)
            movies.layoutManager = layoutManager


//            movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy > 0 && layoutManager.findLastVisibleItemPosition() + 4 >= layoutManager.itemCount) {
//                        viewModel?.loadMore()
//                    }
//                }
//            })

            movies.addOnScrollListener(object : EndlessScrollEventListener(layoutManager) {
                override fun onLoadMore(pageNum: Int, recyclerView: RecyclerView?) {
                  viewModel?.loadMore()
                }
            })

            movies.adapter = adapter

            refresher.setOnRefreshListener {
                viewModel?.refresh()
            }

            lifecycleScope.launch {
                viewModel?.uiState?.collect { uiState ->
                    when (uiState) {
                        is MoviesUiState.Loading -> {
                            Log.e(TAG, "Loading more...")

                        }
                        is MoviesUiState.Success -> {
                            refresher.isRefreshing = false
                            adapter.setItems(uiState.movies)
                        }
                        is MoviesUiState.Error -> {
                            refresher.isRefreshing = false
                            Log.e(TAG, uiState.error.message ?: "Unknown error!")
                        }
                    }
                }
            }
        }
    }

}