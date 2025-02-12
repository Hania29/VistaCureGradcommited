package com.example.vistacuregrad.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://f787-197-53-98-82.ngrok-free.app/"
val interceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.
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