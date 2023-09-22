package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val id : Int = 0,
    val id_user : Int,
    val amount : Int,
    val busines_name : String,
    val busines_socmed : String,
    val busines_desc : String,
    val todo : String,
    val payment_method : String,
):Parcelable
