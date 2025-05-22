package com.sw.placeholder.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object FavoriteManager {
    private val Context.dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> by androidx.datastore.preferences.preferencesDataStore(
        name = "favorites"
    )
    private val FAVORITES_KEY =
        androidx.datastore.preferences.core.stringSetPreferencesKey("favorite_pokemon")

    fun getFavorites(context: Context): Flow<Set<String>> =
        context.dataStore.data.map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun addFavorite(context: Context, name: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            current.add(name)
            preferences[FAVORITES_KEY] = current
        }
    }

    suspend fun removeFavorite(context: Context, name: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            current.remove(name)
            preferences[FAVORITES_KEY] = current
        }
    }

    suspend fun isFavorite(context: Context, name: String): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[FAVORITES_KEY]?.contains(name) == true
    }
}