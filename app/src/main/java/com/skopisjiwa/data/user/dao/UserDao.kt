package com.skopisjiwa.data.user.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skopisjiwa.data.user.local.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser() : Flow<List<User>>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun loginUser(email : String, password : String) : Flow<User>

    @Update
    fun updateUser(user : User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users : List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerUser(user : User)

    @Delete
    fun deleteUsers()
}