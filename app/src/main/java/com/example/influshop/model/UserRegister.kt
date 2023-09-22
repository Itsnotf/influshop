package com.example.influshop.model

import okhttp3.MultipartBody

data class UserRegister(
    val id: Int = 0,
    val email: String = "",
    val password: String = "",
    val name:String = "",
    val profile_picture: MultipartBody.Part
)
