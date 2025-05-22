package com.sw.placeholder.data.repo

import com.sw.placeholder.data.source.AppRemoteDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppDataRepositoryImpl @Inject constructor(private val appRemoteDataSource: AppRemoteDataSource):AppDataRepository {
    override fun getList() = flow {
        emit(appRemoteDataSource.getList())
    }

    override fun getDetail(name:String) = flow {
        emit(appRemoteDataSource.getDetail(name))
    }
}