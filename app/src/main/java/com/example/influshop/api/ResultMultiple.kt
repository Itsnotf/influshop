package com.example.influshop.api

data class ResultMultiple<T>(
    val success: Int,
    val message: String,
    val data: ArrayList<T>,
)
