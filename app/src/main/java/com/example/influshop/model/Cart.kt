package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val id : Int = 0,
    val user_name : String,
    val influencer_name : String,
    val busines_name : String,
    val busines_socmed : String,
    val busines_desc : String,
    val todo : String,
    val price: Int,
    val payment_status : String
):Parcelable
