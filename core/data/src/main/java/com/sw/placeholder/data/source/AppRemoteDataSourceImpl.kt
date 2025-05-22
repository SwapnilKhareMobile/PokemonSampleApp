package com.sw.placeholder.data.source

import android.util.Log
import com.sw.placeholder.model.AppResponse
import com.sw.placeholder.model.Result
import com.sw.placeholder.model.detail.AppDetailResponse
import com.sw.placeholder.network.AppAPI
import javax.inject.Inject

class AppRemoteDataSourceImpl @Inject constructor(private val appAPI: AppAPI):AppRemoteDataSource {
    override suspend fun getList(): List<Result> {
        return appAPI.getPokemonAPI().body()?.results?: emptyList()
    }

    override suspend fun getDetail(name:String): AppDetailResponse? {
        return appAPI.getPokemonDetailAPI(name).body()
    }
}