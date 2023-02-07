package com.example.cardapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetroInstance {
    fun apiService(): ApiService{
        return Retrofit.Builder()
            .baseUrl("https://63e0c14cdd7041cafb38629e.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}