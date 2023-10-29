package com.enigma.enigmamedia.viewmodel.maps

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.di.Injection
import com.enigma.enigmamedia.repository.Repository
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MapsViewModelMVVM(private val repository: Repository) : ViewModel() {

    private val _storyListLiveData = MutableLiveData<List<ListStoryItem>>()
    val storyListLiveData: LiveData<List<ListStoryItem>>
        get() = _storyListLiveData

    fun getStoryLocation(token: String) {
        val token = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = repository.getStoryLocationFromRepo(token)

                if (response.isSuccessful) {
                    val stories = response.body()?.listStory
                    _storyListLiveData.value = stories ?: emptyList()
                }
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
            }
        }
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModelMVVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModelMVVM(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}