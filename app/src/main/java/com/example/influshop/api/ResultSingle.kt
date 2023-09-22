package com.example.influshop.api

data class ResultSingle<T>(
    val success: Int,
    val message: String,
    val data: T,
)