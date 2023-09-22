package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id : Int = 0,
    val id_user : Int,
    val id_influencer : Int,
):Parcelable
