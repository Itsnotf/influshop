package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val id: Int,
    val packet: String,
    val qty: String,
    val price: String,
    val description: String,
    val created_at: String,
    val updated_at: String
):Parcelable
