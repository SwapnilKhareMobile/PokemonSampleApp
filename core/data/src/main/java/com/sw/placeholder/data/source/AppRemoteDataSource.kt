package com.sw.placeholder.data.source

import com.sw.placeholder.model.Result
import com.sw.placeholder.model.detail.AppDetailResponse

interface AppRemoteDataSource {

    suspend fun getList(): List<Result>
    suspend fun getDetail(name:String): AppDetailResponse?
}