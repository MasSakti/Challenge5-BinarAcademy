package com.mutawalli.challenge5.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    fun readLogin(email: String, password: String): UserEntity

    @Update
    suspend fun update(user: UserEntity)
}