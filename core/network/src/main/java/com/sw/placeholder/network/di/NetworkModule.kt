package com.sw.placeholder.network.di

import com.sw.placeholder.common.BASE_URL
import com.sw.placeholder.network.AppAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): AppAPI {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(networkInterceptor())
       return Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .client(okHttpClient.build())
           .build()
           .create(AppAPI::class.java)

    }

    private fun networkInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }
}