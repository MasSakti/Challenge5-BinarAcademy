package com.mutawalli.challenge5.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mutawalli.challenge5.data.local.UserEntity
import com.mutawalli.challenge5.model.Resource
import com.mutawalli.challenge5.repo.UserRepository
import java.util.concurrent.Executors

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private var _loginStatus = MutableLiveData<Resource<UserEntity>>()
    val loginStatus: LiveData<Resource<UserEntity>> get() = _loginStatus

    fun login(email: String, password: String) {

        val executor = Executors.newFixedThreadPool(1)
        executor.execute {
            _loginStatus.postValue(Resource.loading(null))
            try {
                val data = repository.verifyLogin(email, password)
                _loginStatus.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                _loginStatus.postValue(Resource.error(null, exception.message!!))
            }
        }
    }

}