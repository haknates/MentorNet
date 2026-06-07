package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Ngrok kullanıyorsanız Ngrok linkini, Hotspot kullanıyorsanız senin IP'ni buraya yazın
    // DİKKAT: Sonunda eğik çizgi (/) OLMASIN!
    private const val BASE_URL = "http://127.0.0.1:4040.ngrok-free.app"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

