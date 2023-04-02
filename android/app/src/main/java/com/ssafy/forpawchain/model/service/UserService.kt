package com.ssafy.forpawchain.model.service

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import com.ssafy.forpawchain.behind.activity.LoginActivity
import com.ssafy.forpawchain.model.domain.signUpRequestDTO
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import com.ssafy.forpawchain.util.PreferenceManager
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    companion object {
        val TAG: String? = this::class.qualifiedName
        const val baseUrl: String = "http://j8a207.p.ssafy.io:8080/api/"

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getPawHistory(token : String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.getMyPawHistoryList()
    }

    fun setDoctor(doctorDTO: RequestDoctorDTO, token : String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.setDoctor(doctorDTO)
    }

    fun getDoctorName(wa: String, token : String): Call<JsonObject> {
//        val sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
//        val token: String = sharedPreferences.getString("token", "") ?: ""

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.getDoctorName(wa)
    }

    fun getUser(token : String): Call<JsonObject> {
//        val sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
//        val token: String = sharedPreferences.getString("token", "") ?: ""
//
//        Log.d(TAG, "sharedPreferences에 token 잘 가져 옴?" + sharedPreferences.getString("token", "")?:"")

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()
        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);
        return service.getUser()
    }

    fun signUpAndLoginUser(signUpRequestDTO: signUpRequestDTO): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.signUpUser(signUpRequestDTO)
    }

    fun signOutUser(token : String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.signOutUser()
    }

    fun logoutUser(token : String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java);

        return service.logoutUser()
    }

    fun reissue(token : String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(RetrofitService::class.java)

        return service.renewAccessToken()
    }

//    fun generalLogin(loginUserReqDto: LoginUserReqDto): Call<JsonObject> {
//        val client = OkHttpClient.Builder().addInterceptor { chain ->
//            val newRequest = chain.request().newBuilder()
//                .build()
//            chain.proceed(newRequest)
//        }.build()
//        retrofit = Retrofit.Builder()
//            .client(client)
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        service = retrofit.create(RetrofitService::class.java);
//        return service.getUser()
//    }
}