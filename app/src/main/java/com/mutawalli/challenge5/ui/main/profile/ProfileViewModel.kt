package com.mutawalli.challenge5.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mutawalli.challenge5.data.local.UserEntity
import com.mutawalli.challenge5.model.SharedPreference

class ProfileViewModel(
    private val sharedPreference: SharedPreference,
) : ViewModel() {
    private var _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private var _address = MutableLiveData<String>()
    val address: LiveData<String> get() = _address

    private var _fullname = MutableLiveData<String>()
    val fullname: LiveData<String> get() = _fullname

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int> get() = _id

    private var _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    fun getUserData() {
        _email.value = sharedPreference.getPrefKey("email")
        _id.value = sharedPreference.getPrefKeyId("id")
        _address.value = sharedPreference.getPrefKey("address")
        _fullname.value = sharedPreference.getPrefKey("fullname")
        _date.value = sharedPreference.getPrefKey("date")
        _username.value = sharedPreference.getPrefKey("username")
        _password.value = sharedPreference.getPrefKey("password")
    }

    fun sendDataToUpdate(): UserEntity {
        return UserEntity(
            id = id.value!!,
            username = username.value!!,
            email = email.value!!,
            address = address.value!!,
            fullname = fullname.value!!,
            ttl = date.value!!,
            password = password.value!!
        )

    }
}