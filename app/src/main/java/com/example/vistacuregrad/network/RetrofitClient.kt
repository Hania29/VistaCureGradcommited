package com.example.vistacuregrad.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://2d89-197-53-98-82.ngrok-free.app/"
    val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
//         this.level = HttpLoggingInterceptor.Level.HEADERS
//         this.level = HttpLoggingInterceptor.Level.BASIC
    }

    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}