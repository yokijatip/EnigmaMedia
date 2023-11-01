package com.enigma.enigmamedia.data.remote.api

import com.enigma.enigmamedia.data.remote.response.AddResponse
import com.enigma.enigmamedia.data.remote.response.DetailResponse
import com.enigma.enigmamedia.data.remote.response.LoginResponse
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("stories/{id}")
    fun getDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<DetailResponse>

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Query("location") location: Int = 1
    ): Response<AddResponse>

    @Multipart
    @POST("stories")
    fun addNewStoryNoCoroutine(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<AddResponse>


//    Menggunakan MVVM

    @GET("stories")
    suspend fun getAllStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<StoryResponse>


    @GET("stories")
    suspend fun getAllStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ): Response<StoryResponse>
}