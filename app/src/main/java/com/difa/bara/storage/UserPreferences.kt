package com.difa.bara.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.user: DataStore<Preferences> by preferencesDataStore(name = "userPreference")

object DataStoreKeys {
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
    val TOKEN = stringPreferencesKey("token")
}