package com.sdsol.tmdbandroidchallenge.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.MoviesResponse
import com.sdsol.tmdbandroidchallenge.models.movieslistresponse.Result
import com.sdsol.tmdbandroidchallenge.network.BackEndApi
import com.sdsol.tmdbandroidchallenge.utils.BaseViewModel
import com.sdsol.tmdbandroidchallenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application, val retrofitClient : BackEndApi) : BaseViewModel(application) {
    private val _getMoviesResponse: MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    private val _goToDetails: MutableLiveData<Int> = MutableLiveData()

    val getMoviesResponse: LiveData<Resource<List<Result>>>
        get() = _getMoviesResponse

    val goToDetails: LiveData<Int>
        get() = _goToDetails

    init {
        getMovies()
    }

    private fun getMovies(){
        viewModelScope.launch(Dispatchers.IO + handler) {
            _getMoviesResponse.postValue(Resource.Loading())
            val response = retrofitClient.getMovies(1)
            if (response.isSuccessful){
                _getMoviesResponse.postValue(Resource.Success(response.body()!!.results!!))
            } else {
                _getMoviesResponse.postValue(Resource.Error(response.message()))
            }
        }
    }

    fun goToMovieDetail(id: Int){
        _goToDetails.value = id
    }
}