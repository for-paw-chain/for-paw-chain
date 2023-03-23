package com.ssafy.forpawchain.model.service.retrofit

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {
    //    @FormUrlEncoded
//    @Headers(
//        "Content-Type: application/json"
//    )
    // @Query("msg") msg: String


    @GET("test")
    fun test(): Call<JsonObject>

    @GET("query/{msg}")
    fun test(@Path("msg") msg: String): Call<JsonObject>

    @GET("adopt")
    fun getAdoptList(
        @Query("pageno") pageno: Int,
        @Query("sex") sex: String?,
        @Query("spayed") spayed: Boolean?,
        @Query("type") type: String?
    ): Call<JsonObject>

    @GET("adopt/article")
    fun getMyPawHistoryList(

    ): Call<JsonObject>

    @GET("adopt/{pid}")
    fun getDetailAdopt(@Path("pid") pid: String): Call<JsonObject>

    @POST("web3/license")
    fun setDoctor(@Body doctor: RequestDoctorDTO): Call<JsonObject>

    @POST("upload")
    fun setUpload(@Part filePart: MultipartBody.Part): Call<JsonObject>

    @GET("pet/")
    fun getMyPets(): Call<JsonArray>
}