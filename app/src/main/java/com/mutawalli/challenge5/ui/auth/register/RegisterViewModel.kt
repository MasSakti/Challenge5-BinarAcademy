package com.mutawalli.challenge5.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutawalli.challenge5.data.local.UserEntity
import com.mutawalli.challenge5.model.Event
import com.mutawalli.challenge5.model.Resource
import com.mutawalli.challenge5.repo.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private var _registerStatus = MutableLiveData<Resource<UserEntity>>()
    val registerStatus: LiveData<Resource<UserEntity>> get() = _registerStatus
    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun save(
        email: String,
        username: String,
        fullname: String,
        ttl: String,
        address: String,
        password: String
    ) {
        if (email.isEmpty() || username.isEmpty() || fullname.isEmpty() || ttl.isEmpty() || address.isEmpty() || password.isEmpty()) {
            _saved.value = Event(false)
            return
        }

        val user = UserEntity(
            email = email,
            username = username,
            fullname = fullname,
            ttl = ttl,
            address = address,
            password = password
        )

        viewModelScope.launch {
            repository.save(user)
        }
        _saved.value = Event(true)
    }
}