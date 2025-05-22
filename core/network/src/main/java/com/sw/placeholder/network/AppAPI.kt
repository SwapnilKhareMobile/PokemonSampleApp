package com.sw.placeholder.network

import com.sw.placeholder.common.POKEMON_API
import com.sw.placeholder.common.POKEMON_DETAIL_API
import com.sw.placeholder.model.AppResponse
import com.sw.placeholder.model.detail.AppDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppAPI {

    @GET(POKEMON_API)
    suspend fun getPokemonAPI(): Response<AppResponse>

    @GET(POKEMON_DETAIL_API)
    suspend fun getPokemonDetailAPI(@Path("name") name: String): Response<AppDetailResponse>
}