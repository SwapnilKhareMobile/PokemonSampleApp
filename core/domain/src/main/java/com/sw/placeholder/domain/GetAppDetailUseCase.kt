package com.sw.placeholder.domain

import android.content.Context
import com.sw.placeholder.model.detail.AppDetailResponse
import kotlinx.coroutines.flow.Flow

interface GetAppDetailUseCase {
    operator fun invoke(name: String): Flow<AppDetailResponse?>
    fun getFavorites(context: Context): Flow<Set<String>>
    suspend fun addFavorite(name: String , context: Context)
    suspend fun removeFavorite(name: String, context: Context)

}