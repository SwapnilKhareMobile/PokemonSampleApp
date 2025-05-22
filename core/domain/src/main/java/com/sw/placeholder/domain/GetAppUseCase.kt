package com.sw.placeholder.domain

import com.sw.placeholder.model.Result
import kotlinx.coroutines.flow.Flow

interface GetAppUseCase {
    operator fun invoke(): Flow<List<Result>>
}