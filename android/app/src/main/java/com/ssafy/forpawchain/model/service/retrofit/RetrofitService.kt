package com.ssafy.forpawchain.model.service.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {
    //    @FormUrlEncoded
//    @Headers(
//        "Content-Type: application/json"
//    )
    // @Query("msg") msg: String
    @GET("/test")
    fun test(): Call<JsonObject>

    @GET("/query/{msg}")
    fun test(@Path("msg") msg: String): Call<JsonObject>
}