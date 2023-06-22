package com.skopisjiwa.di

import com.skopisjiwa.data.datastore.DataStoreImp
import com.skopisjiwa.data.datastore.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Singleton
    @Binds
    abstract fun bindDataStore(dataStoreImpl: DataStoreImp): DataStoreRepository
}