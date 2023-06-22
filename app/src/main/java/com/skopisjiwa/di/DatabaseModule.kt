package com.skopisjiwa.di

import android.content.Context
import androidx.room.Room
import com.skopisjiwa.data.cart.CartDao
import com.skopisjiwa.data.cart.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideCartDao(database: CartDatabase) : CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            "cart.db"
        )
            .build()
    }

}