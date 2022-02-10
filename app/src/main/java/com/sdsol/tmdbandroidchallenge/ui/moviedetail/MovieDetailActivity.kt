package com.sdsol.tmdbandroidchallenge.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.databinding.ActivityMainBinding
import com.sdsol.tmdbandroidchallenge.databinding.ActivityMovieDetailBinding
import com.sdsol.tmdbandroidchallenge.ui.home.MainViewModel
import com.sdsol.tmdbandroidchallenge.utils.Constants
import com.sdsol.tmdbandroidchallenge.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import observeOnce

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    var movieID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setupDataBinding()

        intent?.let {
           movieID = it.getIntExtra(Constants.ID, -1)
        }

        viewModel.getMovieDetail(movieID)

        viewModel.getMovieDetailResponse.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> binding.pbLoading.visibility = View.VISIBLE
                is Resource.Error -> {
                    Toast.makeText(this, resource.error, Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                }
                is Resource.Success -> binding.pbLoading.visibility = View.GONE
            }
        })

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.movieDetailViewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}