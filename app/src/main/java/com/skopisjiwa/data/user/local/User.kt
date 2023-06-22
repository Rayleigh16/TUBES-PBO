package com.skopisjiwa.data.user.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id : Int,
    val roleId : Int,
    val email : String,
    val password : String,
    val name : String,
    val number : String,
    val address : String,
)
