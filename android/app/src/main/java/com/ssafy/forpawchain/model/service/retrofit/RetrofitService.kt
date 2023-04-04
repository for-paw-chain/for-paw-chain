package com.ssafy.forpawchain.model.service.retrofit

import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.signUpRequestDTO
import com.ssafy.forpawchain.model.domain.LoginUserReqDTO
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

    @Multipart
    @POST("upload")
    fun setUpload(@Part filePart: MultipartBody.Part): Call<JsonObject>

    @GET("pet/")
    fun getMyPets(): Call<JsonObject>

    @GET("pet/info")
    fun getPetInfo(@Query("pid") pid: String): Call<JsonObject>

    @GET("auth")
    fun getPetAuth(@Query("pid") pid: String): Call<JsonObject>
    @PUT("auth")
    fun removePetAuth(@Query("receiver") target: Int,
                      @Query("pid") pid: String): Call<JsonObject>
    @PUT("auth/hand")
    fun handPetAuth(@Query("receiver") receiver: Int,
                      @Query("pid") pid: String
    ): Call<JsonObject>
    @POST("auth")
    fun giveFriendAuth(@Query("receiver") receiver: Int,
                       @Query("pid") pid: String
    ): Call<JsonObject>
    @GET("web3/wallet/{wa}")
    fun getDoctorName(@Path("wa") wa: String): Call<JsonObject>
    @GET("auth/date")
    fun getRegDateAuth(@Query("uid") uid: Int,
                       @Query("pid") pid: String
    ): Call<JsonObject>

    @DELETE("adopt/{pid}")
    fun deleteAdopt(@Path("pid") pid: String): Call<JsonObject>

    @Multipart
    @POST("adopt")
    fun createAdopt(
        @Part image: MultipartBody.Part,
        @Part payload: MultipartBody.Part
    ): Call<JsonObject>

    @POST("web3/contract/{pid}")
    fun getCA(@Path("pid") pid: String): Call<JsonObject>

    //로그인 한 회원 정보 조회
    @GET("user/")
    fun getUser() : Call<JsonObject>

    //SNS 회원가입 & 로그인
    @POST("user/")
    fun signUpUser(
        @Body signUpRequestDTO: signUpRequestDTO
    ): Call<JsonObject>

    //회원 탈퇴
    @DELETE("user/")
    fun signOutUser(): Call<JsonObject>

    // 로그아웃
    @GET("user/logout/")
    fun logoutUser(): Call<JsonObject>

    @POST("user/reissue")
    fun renewAccessToken(): Call<JsonObject>

    @POST("user/login")
    fun generalLogin(
        @Body loginUserReqDto: LoginUserReqDTO
    ): Call<JsonObject>

    @Multipart
    @POST("pet/info")
    fun createPawInfo(
        @Part image: MultipartBody.Part? = null,
        @Part payload: MultipartBody.Part
    ): Call<JsonObject>
}