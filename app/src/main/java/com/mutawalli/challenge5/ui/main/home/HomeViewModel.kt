package com.mutawalli.challenge5.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mutawalli.challenge5.data.api.MovieResponse
import com.mutawalli.challenge5.data.api.Movies
import com.mutawalli.challenge5.network.MovieApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var _popular = MutableLiveData<List<Movies>>()
    val popular: LiveData<List<Movies>> get() = _popular

    private var _errorStatus = MutableLiveData<Boolean>()
    val errorStatus: LiveData<Boolean> get() = _errorStatus

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        getPopularMovie()
    }

    fun getPopularMovie() {
        MovieApiClient.instance.getPopular().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                _loading.value = true
                when {
                    response.isSuccessful -> {
                        response.body()?.results?.let {
                            _loading.value = false
                            _popular.value = it
                            _errorStatus.value = false
                        }
                    }
                    response.code() == 401 -> {
                        _loading.value = false
                        _errorStatus.value = true
                    }
                    response.code() == 404 -> {
                        _loading.value = false
                        _errorStatus.value = true
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _errorStatus.value = true
                _loading.value = false
            }
        })
    }
}