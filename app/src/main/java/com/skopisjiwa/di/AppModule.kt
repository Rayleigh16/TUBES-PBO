package com.skopisjiwa.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.firestore.FirebaseFirestore
import com.skopisjiwa.data.bill.repository.MitraRepository
import com.skopisjiwa.data.cart.CartDao
import com.skopisjiwa.data.repository.admin.AdminRepository
import com.skopisjiwa.data.repository.cart.CartRepository
import com.skopisjiwa.data.repository.cart.CartRepositoryImpl
import com.skopisjiwa.data.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val Context.userPreference by preferencesDataStore(name = "preference")

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.userPreference

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    @Singleton
    fun provideUserRepository() = UserRepository()

    @Provides
    @Singleton
    fun provideAdminRepository(
        firestore: FirebaseFirestore
    ) = AdminRepository(firestore)


    @Provides
    @Singleton
    fun mitraRepository(
        firestore: FirebaseFirestore
    ) = MitraRepository(firestore)



    @Provides
    @Singleton
    fun provideFiresStoreDB() = FirebaseFirestore.getInstance()

    @Provides
    fun providesCartRepository(cartDao: CartDao) : CartRepository = CartRepositoryImpl(cartDao)
}