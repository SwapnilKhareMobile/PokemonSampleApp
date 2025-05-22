package com.sw.placeholder.data.di

import com.sw.placeholder.data.repo.AppDataRepository
import com.sw.placeholder.data.repo.AppDataRepositoryImpl
import com.sw.placeholder.data.source.AppRemoteDataSource
import com.sw.placeholder.data.source.AppRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {


    @Binds
    abstract fun bindRepository(impl: AppDataRepositoryImpl): AppDataRepository

    @Binds
    abstract fun bindRemoteDataSource(impl: AppRemoteDataSourceImpl): AppRemoteDataSource

}