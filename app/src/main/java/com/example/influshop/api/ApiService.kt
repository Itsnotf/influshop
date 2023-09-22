package com.example.influshop.api

import com.example.influshop.model.*
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiService {
    companion object{
        const val BASE_URL = "https://itfest.cyborgitcenter.org/"
        private const val BASE_URL_API = BASE_URL + "api/"
    }
    private val gson = GsonBuilder().setDateFormat("yyy-MM-dd HH:mm:ss").create()
    private val api = Retrofit.Builder().baseUrl(BASE_URL_API)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(Api::class.java)

    fun login(user: User): Single<ResultSingle<User>>{
        return api.login(user)
    }

    fun register(profilePicture: MultipartBody.Part,
                 name: RequestBody,
                 email: RequestBody,
                 nohp: RequestBody,
                 password: RequestBody
    ): Observable<ResultSingle<UserRegister>> {
        return api.register(profilePicture, name, email, nohp, password)
    }

    fun ambilPacket():Single<ResultMultiple<Packet>>{
        return api.ambilPacket()
    }

    fun ambilPacketInfluencer(influencer_id:Int):Single<ResultMultiple<Packet>>{
        return api.ambilInfluencerPacket(influencer_id)
    }

    fun orderKeranjang(order: Order):Single<ResultSingle<Order>>{
        return api.orderKeranjang(order)
    }

    fun bayar(user_id: Int,bukti_tf: MultipartBody.Part, busines_name: RequestBody, busines_socmed: RequestBody, busines_desc: RequestBody, todo: RequestBody,amount: RequestBody,payment_method:RequestBody): Observable<ResultSingle<Payment>> {
        return api.bayar(user_id,bukti_tf, busines_name, busines_socmed, busines_desc, todo,amount,payment_method)
    }

    fun history(user_id: Int):Single<ResultMultiple<History>>{
        return api.history(user_id)
    }

    fun ambilOrderByIdUser(user_id:Int):Single<ResultMultiple<Cart>>{
        return  api.ambilOrderByIdUser(user_id)
    }

    fun influencer():Single<ResultMultiple<Influencer>>{
        return api.influencer()
    }

    fun profile(email:String):Single<ResultSingle<UserProfile>>{
        return api.profile(email)
    }

    fun detail():Single<ResultMultiple<Detail>>{
        return api.detail()
    }
}