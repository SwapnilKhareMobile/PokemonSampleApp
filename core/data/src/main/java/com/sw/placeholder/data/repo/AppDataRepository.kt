package com.sw.placeholder.data.repo

import com.sw.placeholder.model.Result
import com.sw.placeholder.model.detail.AppDetailResponse
import kotlinx.coroutines.flow.Flow

interface AppDataRepository {

     fun getList(): Flow<List<Result>>

     fun getDetail(name:String): Flow<AppDetailResponse?>

}