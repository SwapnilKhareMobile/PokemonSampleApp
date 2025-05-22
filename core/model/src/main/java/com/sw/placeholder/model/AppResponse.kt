package com.sw.placeholder.model

data class AppResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)