package com.skopisjiwa.data.user

data class UserModel(
    val id : String,
    val roleId : Int,
    val email : String,
    val password : String,
    val name : String,
    val number : String,
    val address : String,
)