package com.sw.placeholder.domain

import android.content.Context
import com.sw.placeholder.data.repo.AppDataRepository
import com.sw.placeholder.model.detail.AppDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAppDetailUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetAppDetailUseCase {
    override operator fun invoke(name: String): Flow<AppDetailResponse?> {
        return appDataRepository.getDetail(name).flowOn(Dispatchers.IO)
    }

    override fun getFavorites(context: Context): Flow<Set<String>> {
       return FavoriteManager.getFavorites(context)
    }

    override suspend fun addFavorite(name: String, context: Context) {
        FavoriteManager.addFavorite(context, name)
    }

    override suspend fun removeFavorite(name: String, context: Context) {
        FavoriteManager.removeFavorite(context, name)
    }
}