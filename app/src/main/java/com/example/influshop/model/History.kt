package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    val user: String,
    val influencer: String,
    val busines_name: String,
    val busines_socmed: String,
    val busines_desc: String,
    val todo: String,
    val total: String,
    val payment_status: String,
    val created_at: String,
    val updated_at: String
): Parcelable
