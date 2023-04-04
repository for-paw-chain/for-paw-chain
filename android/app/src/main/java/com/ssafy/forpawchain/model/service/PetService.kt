package com.ssafy.forpawchain.model.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetService {
    companion object {
        val TAG: String? = this::class.qualifiedName

        const val baseUrl: String = "http://j8a207.p.ssafy.io:8080/api/"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getMyPets(token : String): Call<JsonObject> {
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

        return service.getMyPets()
    }

    fun getPetInfo(pid: String, token: String): Call<JsonObject> {
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

        return service.getPetInfo(pid)
    }
    fun createPawInfo(
        image: MultipartBody.Part? = null,
        payload: MultipartBody.Part,
        token: String
    ): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(newRequest)
        }.build()

        PetService.retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PetService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        PetService.service = PetService.retrofit.create(RetrofitService::class.java);

        return PetService.service.createPawInfo(image, payload)
    }
}