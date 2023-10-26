package com.enigma.enigmamedia.repository

import com.enigma.enigmamedia.data.remote.api.ApiService
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    suspend fun getStoryFromRepository(token: String): Response<StoryResponse> {
        return apiService.getAllStory(token)
    }

}