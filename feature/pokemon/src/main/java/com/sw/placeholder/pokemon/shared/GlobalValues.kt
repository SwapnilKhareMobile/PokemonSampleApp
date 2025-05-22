package com.sw.placeholder.comments.shared

import com.sw.placeholder.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalValues @Inject constructor() {

    private val _sharedData = MutableStateFlow<Result?>(null)
    val sharedData: StateFlow<Result?> = _sharedData.asStateFlow()

    fun setSharedData(value: Result) {
        _sharedData.value = value
    }
}