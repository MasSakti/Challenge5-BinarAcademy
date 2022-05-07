package com.mutawalli.challenge5.repo

import android.content.Context
import com.mutawalli.challenge5.data.local.UserDAO
import com.mutawalli.challenge5.data.local.UserEntity
import com.mutawalli.challenge5.database.MovieRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDAO) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository? {
            return instance ?: synchronized(UserRepository::class.java) {
                if (instance == null) {
                    val database = MovieRoomDatabase.getInstance(context)
                    instance = UserRepository(database.userDao())
                }
                return instance
            }
        }
    }

    suspend fun save(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    suspend fun update(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.update(user)
        }
    }

    fun verifyLogin(email: String, password: String): UserEntity {
        return userDao.readLogin(email, password)
    }
}