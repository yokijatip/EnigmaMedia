package com.enigma.enigmamedia.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.data.remote.response.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class MainViewModel : ViewModel() {

    private val storyListLiveData = MutableLiveData<List<ListStoryItem>>()

    fun getStoryListLiveData(): LiveData<List<ListStoryItem>> {
        return storyListLiveData
    }


    fun getAllStory(token : String) {

        val token = "Bearer $token"

        Client.getApiService()
            .getStories(token)
            .enqueue(object : Callback<StoryResponse> {
                override fun onResponse(
                    call: Call<StoryResponse>,
                    response: Response<StoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val stories = response.body()?.listStory
                        storyListLiveData.value = stories!!
                    }
                }

                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    t.message?.let { Log.e("Mana User nya juga ga ada🫵😞", it)}
                }
            })
    }

}