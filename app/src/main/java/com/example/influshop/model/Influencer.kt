package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Influencer(
    val id : Int,
    val name: String,
    val photo: String,
    val bio: String,
    val tiktok: String,
    val instagram : String,
    val facebook : String,
    val created_at: String,
    val updated_at: String,
):Parcelable
