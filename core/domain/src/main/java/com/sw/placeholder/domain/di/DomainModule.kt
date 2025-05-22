package com.sw.placeholder.domain.di

import com.sw.placeholder.domain.GetAppDetailUseCase
import com.sw.placeholder.domain.GetAppDetailUseCaseImpl
import com.sw.placeholder.domain.GetAppUseCase
import com.sw.placeholder.domain.GetAppUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetListUseCase(impl: GetAppUseCaseImpl): GetAppUseCase

    @Binds
    abstract fun bindGetDetailUseCase(impl: GetAppDetailUseCaseImpl): GetAppDetailUseCase

}