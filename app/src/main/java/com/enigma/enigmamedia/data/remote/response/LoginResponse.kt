package com.enigma.enigmamedia.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("loginResult")
    val loginResult: LoginResult

)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)
