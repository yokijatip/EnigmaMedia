package com.enigma.enigmamedia.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.DetailResponse
import com.enigma.enigmamedia.data.remote.response.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val storyDetailLiveData = MutableLiveData<Story>()

    fun getStoryDetail(token: String, id: String) {

        val token = "Bearer $token"

        Client.getApiService()
            .getDetail(token, id)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val storyDetail = response.body()?.story
                        storyDetailLiveData.value = storyDetail!!
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                }
            })
    }

    fun getStoryDetailLiveData(): LiveData<Story> {
        return storyDetailLiveData
    }

}