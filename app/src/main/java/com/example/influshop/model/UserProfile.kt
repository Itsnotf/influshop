package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val id: Int = 0,
    val name:String = "",
    val email: String = "",
    val nohp: String = "",
    val profile_pict: String = "",
    val created_at:String = "",
    val updated_at : String = ""
):Parcelable
