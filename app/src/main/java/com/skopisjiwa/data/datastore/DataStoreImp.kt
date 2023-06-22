package com.skopisjiwa.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreImp @Inject constructor(
    private val dataStore : DataStore<Preferences>
) : DataStoreRepository {

    override fun getIsLogin(): Flow<Boolean> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val isLogin = it[IS_LOGIN] ?: true
            isLogin
        }
    }


    override suspend fun setIslogin(isLogin: Boolean) {
        dataStore.edit {
            it[IS_LOGIN] = isLogin
        }
    }

    override fun getRoleId(): Flow<Long> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val roleId = it[ROLE_ID] ?: 0
            roleId
        }
    }

    override suspend fun setRoleId(roleId: Long) {
        dataStore.edit {
            it[ROLE_ID] = roleId
        }
    }

    companion object {
        val IS_LOGIN = booleanPreferencesKey("is_login")
        val ROLE_ID = longPreferencesKey("role_id")
    }
}

interface DataStoreRepository {

    fun getIsLogin(): Flow<Boolean>

    suspend fun setIslogin(isLogin: Boolean)

    fun getRoleId(): Flow<Long>

    suspend fun setRoleId(isLogin: Long)
}