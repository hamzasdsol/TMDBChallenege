package com.sdsol.tmdbandroidchallenge.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdsol.tmdbandroidchallenge.R
import com.sdsol.tmdbandroidchallenge.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    var movieID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        intent?.let {
           movieID = it.getIntExtra(Constants.ID, -1)
        }
    }
}