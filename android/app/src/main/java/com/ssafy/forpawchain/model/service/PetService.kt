package com.ssafy.forpawchain.model.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PetService {
    companion object {
        val TAG: String? = this::class.qualifiedName

        const val baseUrl: String = "http://70.12.247.161:8080/api/"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getMyPets(): Call<JsonArray> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("accessToken", "Bearer qwerqwer")
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
}