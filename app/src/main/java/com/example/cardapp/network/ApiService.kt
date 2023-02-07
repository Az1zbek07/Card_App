package com.example.cardapp.network

import com.example.cardapp.model.CardList
import com.example.cardapp.model.CardResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/api/v1/cards")
    fun getCards(): Call<List<CardResponse>>

    @GET("/api/v1/cards/{id}")
    fun getCardById(@Path("id") id: Int): Call<CardResponse>

    @POST("/api/v1/cards")
    fun postCard(@Body cardResponse: CardResponse): Call<CardResponse>

    @PUT("/api/v1/cards/{id}")
    fun updateCard(@Path("id") id: Int, @Body cardResponse: CardResponse): Call<CardResponse>

    @DELETE("api/v1/cards/{id}")
    fun deleteCard(@Path("id") id: Int): Call<Any>
}