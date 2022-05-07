package com.mutawalli.challenge5.ui.main.home.detailhome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mutawalli.challenge5.data.api.Movies
import com.mutawalli.challenge5.network.MovieApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private var _detail = MutableLiveData<Movies?>()
    val detail: LiveData<Movies?> get() = _detail

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getDetail(id: Int) {
        MovieApiClient.instance.getDetail(id).enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                _loading.value = true
                when {
                    response.code() == 200 -> {
                        _loading.value = false
                        _detail.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                _loading.value = false
            }
        })
    }
}