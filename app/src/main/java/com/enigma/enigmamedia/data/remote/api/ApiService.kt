package com.enigma.enigmamedia.data.remote.api

import android.adservices.adid.AdId
import com.enigma.enigmamedia.data.model.LoginRequest
import com.enigma.enigmamedia.data.model.RegisterRequest
import com.enigma.enigmamedia.data.remote.response.LoginResponse
import com.enigma.enigmamedia.data.remote.response.LoginResult
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

}