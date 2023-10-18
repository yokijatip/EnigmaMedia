package com.enigma.enigmamedia.data.remote.api

import com.enigma.enigmamedia.data.remote.response.LoginResponse
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(

        @Field("email") email: String,
        @Field("password") password: String

    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(

        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,

        ): Call<RegisterResponse>

    @GET("stories")
    fun getStories(

        @Header("Authorization") token: String,

        ): Call<StoryResponse>
}