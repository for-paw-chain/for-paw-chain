package com.ssafy.forpawchain.model.service

import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptService {
    companion object {
        val TAG: String? = this::class.qualifiedName

        const val baseUrl: String = "http://j8a207.p.ssafy.io:8080/api/"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getAdoptList(): Call<JsonObject> {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getAdoptList(0, null, null, null)

    }

    fun getDetailAdopt(pid: String): Call<JsonObject> {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getDetailAdopt(pid)
    }


    /*
        @Multipart
    @POST("profile")
    fun createAdopt(
        @Part image: MultipartBody.Part,
        @PartMap payload: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ResponseBody>
     */
    fun createAdopt(
        image: MultipartBody.Part,
        payload: MultipartBody.Part
    ): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer qwerqwer")
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PetService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.createAdopt(image, payload)
    }

    fun deleteAdopt(pid: String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Access-Token", "Bearer qwerqwer")
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PetService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.deleteAdopt(pid)
    }

    fun getCA(pid: String): Call<JsonObject> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Access-Token", "Bearer qwerqwer")
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PetService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getCA(pid)
    }
}