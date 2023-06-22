package com.skopisjiwa.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartModel::class], exportSchema = false, version = 1)
abstract class CartDatabase : RoomDatabase(){

    abstract fun cartDao() : CartDao

}