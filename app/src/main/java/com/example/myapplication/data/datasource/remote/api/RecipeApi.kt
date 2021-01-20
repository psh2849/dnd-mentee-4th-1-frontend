package com.example.myapplication.data.datasource.remote.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RecipeApi {

    @GET("v1/search/timeline.json")
    fun getAllTimelines(
        @Query("id") id: String? = null
    ): Call<RecipeDTO.TimelineResponse>

    @FormUrlEncoded
    @POST("/posts")
    fun postTimeline(
        @Field("title") title: String,
        @Field("subTitle") subTitle: String,
        @Field("imageUrls") imageUrls: List<String>
    ): Call<RecipeDTO.TimelineResponse>

    companion object {
        private const val BASE_URL = "https://yorijory.com"

        fun create(): RecipeApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApi::class.java)
        }
    }

}