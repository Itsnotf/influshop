package com.example.influshop.api

import com.example.influshop.model.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {
    @POST("login")
    fun login(@Body user: User): Single<ResultSingle<User>>

    @Multipart
    @POST("register")
    fun register(
        @Part profilePicture: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("nohp") nohp: RequestBody,
        @Part("password") password: RequestBody
    ): Observable<ResultSingle<UserRegister>>

    @GET("packet")
    fun ambilPacket(): Single<ResultMultiple<Packet>>

    @GET("showInfluencer")
    fun influencer(): Single<ResultMultiple<Influencer>>

    @POST("orderPost")
    fun orderKeranjang(@Body order: Order): Single<ResultSingle<Order>>

    @GET("orderById/{user_id}")
    fun ambilOrderByIdUser(@Path("user_id") user_id: Int) : Single<ResultMultiple<Cart>>
    @GET("showInluenceById/{influencer_id}")
    fun ambilInfluencerPacket(@Path("influencer_id") user_id:Int): Single<ResultMultiple<Packet>>
    @Multipart
    @POST("updateOrder/{user_id}")
    fun bayar(
        @Path("user_id") user_id: Int,
        @Part bukti_tf: MultipartBody.Part,
        @Part("busines_name") busines_name: RequestBody,
        @Part("busines_socmed") busines_socmed: RequestBody,
        @Part("busines_desc") busines_desc: RequestBody,
        @Part("todo") todo: RequestBody,
        @Part("amount") amount: RequestBody,
        @Part("payment_method") payment_method: RequestBody,
    ): Observable<ResultSingle<Payment>>
    @GET("history/{user_id}")
    fun history(@Path("user_id") user_id: Int):Single<ResultMultiple<History>>

    @GET("profile/{email}")
    fun profile(@Path("email") email:String):Single<ResultSingle<UserProfile>>

    @GET("detail")
    fun detail():Single<ResultMultiple<Detail>>

    @GET("showInfluencer/{influencer_id}")
    fun showInfluencer(@Path("influencer_id")influencer_id:Int):Single<ResultSingle<Influencer>>
}