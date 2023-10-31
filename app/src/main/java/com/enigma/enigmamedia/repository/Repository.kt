package com.enigma.enigmamedia.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.enigma.enigmamedia.data.paging.StoryPagingSource
import com.enigma.enigmamedia.data.remote.api.ApiService
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    suspend fun getStoryLocationFromRepo(token: String): Response<StoryResponse> {
        return apiService.getAllStoryLocation(token, 1)
    }


    fun getStoryPagingSource(token: String): Flow<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).flow
    }
}