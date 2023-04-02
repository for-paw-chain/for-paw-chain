package com.ssafy.forpawchain.model.service

import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.LoginUserReqDTO
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.domain.UserDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
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

    fun getPawHistory(): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun setDoctor(doctorDTO: RequestDoctorDTO): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun getDoctorName(wa: String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer qwerqwer")
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


    fun getUser(): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun signUpUser(userDTO: UserDTO): Call<JsonObject> {
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

        return service.signUpUser(userDTO)
    }

    fun signOutUser(): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun logoutUser(): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun reissue(): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + UserInfo.token)
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

    fun generalLogin(loginUserReqDto: LoginUserReqDTO): Call<JsonObject> {
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
        return service.generalLogin(loginUserReqDto)
    }
}