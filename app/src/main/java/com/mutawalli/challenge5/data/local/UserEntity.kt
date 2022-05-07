package com.mutawalli.challenge5.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize

data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "fullname") val fullname: String = "",
    @ColumnInfo(name = "ttl") val ttl: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "password") val password: String
) : Parcelable