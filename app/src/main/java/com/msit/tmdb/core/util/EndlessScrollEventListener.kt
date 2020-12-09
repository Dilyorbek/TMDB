package com.msit.tmdb.core.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollEventListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    /** is number of items that we could have after our
     * current scroll position before we start loading
     * more items  */
    private val visibleThreshold = 5

    /** to keep track of the page that we would like to
     * retrieve from a server our database
     */
    private var currentPage = 0

    /** total number of items that we retrieve lastly */
    private var previousTotalItemCount = 0

    /** indicating whether we are loading new dataset or not */
    private var loading = true

    /** the initial index of the page that'll start from  */
    private val startingPageIndex = 0
    /******* variables we could get from linearLayoutManager  */
    /** the total number of items that we currently have on our recyclerview and we
     * get it from linearLayoutManager  */
    private var totalItemCount = 0

    /** the position of last visible item in our view currently
     * get it from linearLayoutManager  */
    private var lastVisibleItemPosition = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition()

        // first case
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        // second case
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // third case
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, recyclerView)
            loading = true
        }
    }

    // should be called if we do filter(search) to our list
    fun reset() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    // Define the place where we load the dataset
    abstract fun onLoadMore(pageNum: Int, recyclerView: RecyclerView?)
}