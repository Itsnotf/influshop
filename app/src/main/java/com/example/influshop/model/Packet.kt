package com.example.influshop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Packet(
    val id : Int,
    val id_influencer : Int,
    val packet_name : String,
    val description : String,
    val picture : String,
    val price : String
): Parcelable