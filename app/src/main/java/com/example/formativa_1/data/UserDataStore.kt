package com.example.formativa_1.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserDataStore(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val EMAIL_KEY = stringPreferencesKey("email")
    }

    suspend fun saveCredentials(username: String, password: String) {
        context.userDataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[PASSWORD_KEY] = password

        }
    }

    val getUsername: Flow<String> = context.userDataStore.data.map { it[USERNAME_KEY] ?: "" }
    val getPassword: Flow<String> = context.userDataStore.data.map { it[PASSWORD_KEY] ?: "" }
    val getEmail: Flow<String> = context.userDataStore.data.map { it[EMAIL_KEY] ?: "" }
}
