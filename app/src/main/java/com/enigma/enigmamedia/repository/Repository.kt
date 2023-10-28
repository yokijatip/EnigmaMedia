package com.enigma.enigmamedia.repository

import com.enigma.enigmamedia.data.remote.api.ApiService
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import com.enigma.enigmamedia.data.remote.response.UserLocation
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    suspend fun getStoryFromRepository(token: String): Response<StoryResponse> {
        return apiService.getAllStory(token)
    }

    suspend fun getUserLocation(token: String): Response<UserLocation> {
        return apiService.getStoriesWithLocation(token)
    }

}