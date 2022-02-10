package com.sdsol.tmdbandroidchallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.databinding.ActivityMainBinding
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.Result
import com.sdsol.tmdbandroidchallenge.ui.moviedetail.MovieDetailActivity
import com.sdsol.tmdbandroidchallenge.utils.BasePagingActivity
import com.sdsol.tmdbandroidchallenge.utils.Constants
import com.sdsol.tmdbandroidchallenge.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import observeOnce

@AndroidEntryPoint
class MainActivity : BasePagingActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private var moviesList: MutableList<Result> = mutableListOf()
    private var searchQuery = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMovies(pageNo)
        setupDataBinding()
        setupAdapter()
        initViews(binding.rvMovies)

        viewModel.getMoviesResponse.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> binding.pbLoading.visibility = View.VISIBLE
                is Resource.Error -> {
                    Toast.makeText(this, resource.error, Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    setupList(resource.data!!)
                    moviesAdapter.updateData(moviesList,pageNo)
                }
            }
        })

        binding.rvMovies.apply {
            adapter = moviesAdapter
        }

        binding.etSearch.doOnTextChanged { text, start, before, count ->
            pageNo = 1
            searchQuery = text.toString()
            if (text.toString().isBlank()) {
                if (text.toString().isNotEmpty())
                    return@doOnTextChanged
                viewModel.getMovies(pageNo)
            } else {
                viewModel.searchMovie(text.toString().trim(), pageNo)
            }
        }
    }

    private fun setupList(list: List<Result>) {
        if (pageNo == 1 && moviesList.isNotEmpty()) {
            moviesList.clear()
        }
        moviesList.addAll(list)
        loadMore = true
    }

    override fun loadMoreData() {
        pageNo++
        if (searchQuery.isNotBlank()) {
            viewModel.searchMovie(searchQuery, pageNo)
        } else
            viewModel.getMovies(page = pageNo)
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun setupAdapter() {
        moviesAdapter = MoviesAdapter {
            Intent(this, MovieDetailActivity::class.java).apply {
                putExtra(Constants.ID, it!!)
                startActivity(this)
            }
        }
    }
}