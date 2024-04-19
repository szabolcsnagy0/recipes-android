package com.recipes.api

import com.recipes.data.ResponseData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("/recipes")
    fun getRecipes(): Call<ResponseData?>?

    companion object {
        private const val BASE_URL = "https://dummyjson.com"
        val api: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
        }
    }
}