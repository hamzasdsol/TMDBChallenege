package com.sdsol.tmdbandroidchallenge.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdsol.tmdbandroidchallenge.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private val context
        get() = getApplication<Application>()

    protected val _toast = MutableLiveData<Any?>()
    val toast: LiveData<Any?>
        get() = _toast

    protected val handler = CoroutineExceptionHandler { _, exception ->
        run {
            when (exception) {
                is SocketTimeoutException -> {
                    _toast.postValue(context.resources.getString(R.string.slower_internet_connection))
                }
                is TimeoutException -> {
                    _toast.postValue(context.resources.getString(R.string.slower_internet_connection))
                }
                is HttpException -> {
                    _toast.postValue(context.resources.getString(R.string.unknown_error_occoured))
                }
                else -> {
                    _toast.postValue(exception.message ?: exception.toString())
                }
            }
        }
    }
}