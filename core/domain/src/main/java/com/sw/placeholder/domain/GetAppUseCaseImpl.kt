package com.sw.placeholder.domain

import com.sw.placeholder.data.repo.AppDataRepository
import com.sw.placeholder.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAppUseCaseImpl @Inject constructor(private val appDataRepository: AppDataRepository) :
    GetAppUseCase {

    private val allItems: Flow<List<Result>> =
        appDataRepository.getList().flowOn(Dispatchers.IO)

    override operator fun invoke(): Flow<List<Result>> {
        return allItems
    }

}