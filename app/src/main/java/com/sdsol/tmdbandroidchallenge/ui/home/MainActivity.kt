package com.sdsol.tmdbandroidchallenge.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.databinding.ActivityMainBinding
import com.sdsol.tmdbandroidchallenge.ui.moviedetail.MovieDetailActivity
import com.sdsol.tmdbandroidchallenge.utils.Constants
import com.sdsol.tmdbandroidchallenge.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()
        setupAdapter()

        viewModel.getMoviesResponse.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
                is Resource.Error -> Toast.makeText(this, resource.error, Toast.LENGTH_SHORT).show()
                is Resource.Success -> moviesAdapter.updateData(resource.data!!)
            }
        })

        binding.rvMovies.apply {
            adapter = moviesAdapter
        }
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