package com.sdsol.tmdbandroidchallenge.ui.moviedetail.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdsol.tmdbandroidchallenge.models.moviedetails.MovieDetailResponse
import com.sdsol.tmdbandroidchallenge.network.BackEndApi
import com.sdsol.tmdbandroidchallenge.utils.BaseViewModel
import com.sdsol.tmdbandroidchallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(application: Application, val retrofitClient : BackEndApi) : BaseViewModel(application){
    private val _getMovieDetailResponse: MutableLiveData<Resource<MovieDetailResponse>> = MutableLiveData()
    private val _posterImage: MutableLiveData<String> = MutableLiveData()
    private val _title: MutableLiveData<String> = MutableLiveData()
    private val _releaseDate: MutableLiveData<String> = MutableLiveData()
    private val _revenue: MutableLiveData<String> = MutableLiveData()
    private val _runTime: MutableLiveData<String> = MutableLiveData()
    private val _average: MutableLiveData<String> = MutableLiveData()
    private val _voteCount: MutableLiveData<String> = MutableLiveData()
    private val _overview: MutableLiveData<String> = MutableLiveData()

    val getMovieDetailResponse: LiveData<Resource<MovieDetailResponse>>
        get() = _getMovieDetailResponse

    val posterImage: LiveData<String>
    get() = _posterImage

    val title: LiveData<String>
        get() = _title

    val overview: LiveData<String>
        get() = _overview

    val average: LiveData<String>
        get() = _average

    val voteCount: LiveData<String>
        get() = _voteCount

    val releaseDate: LiveData<String>
        get() = _releaseDate

    val runTime: LiveData<String>
        get() = _runTime

    val revenue: LiveData<String>
        get() = _revenue


    fun getMovieDetail(id : Int){
        viewModelScope.launch(Dispatchers.IO + handler) {
            _getMovieDetailResponse.postValue(Resource.Loading())
            val response = retrofitClient.getMovieDetails(id)
            if (response.isSuccessful){
                _getMovieDetailResponse.postValue(Resource.Success(response.body()!!))
                updateLiveData(response.body()!!)
            } else {
                _getMovieDetailResponse.postValue(Resource.Error(response.message()))
            }
        }
    }

    private fun updateLiveData(detail: MovieDetailResponse){
        detail.apply {
            _posterImage.postValue(backdropPath!!)
            _title.postValue(originalTitle!!)
            _overview.postValue(overview!!)
            _average.postValue(voteAverage!!.toString()+"/10")
            _voteCount.postValue(voteCount!!.toString() + " Votes")
            _releaseDate.postValue(releaseDate!!)
            _runTime.postValue(runtime!!.toString() + " Minutes")
            _revenue.postValue("$" + revenue!!.toString())
        }
    }

}