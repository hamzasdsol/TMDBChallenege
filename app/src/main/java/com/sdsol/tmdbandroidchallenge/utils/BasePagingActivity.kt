package com.sdsol.tmdbandroidchallenge.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

open class BasePagingActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    internal var loadMore = true
    internal var pageNo = 1
    internal var pageSize = 20

    open fun loadMoreData() {}

    open fun initViews(recycler: RecyclerView?) {
        recyclerView = recycler
        if (recyclerView != null) {
            initLoadMore()
        }
    }

    private fun initLoadMore() {
        if (recyclerView!!.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView!!.layoutManager as LinearLayoutManager?
            recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(@NotNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = linearLayoutManager!!.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition()
                    if (loadMore && visibleItemCount + firstVisibleItemPosition >= totalItemCount && totalItemCount >= pageSize && totalItemCount % (pageSize * pageNo) == 0) {
                        loadMoreData()
                        loadMore = false
                    }
                }
            })
        }
    }
}