package com.enigma.enigmamedia.viewmodel.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.di.Injection
import com.enigma.enigmamedia.repository.Repository
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MainViewModelMVVM(private val repository: Repository) : ViewModel() {

    private val _storyListLiveData = MutableLiveData<List<ListStoryItem>>()

    val storyListLiveData: LiveData<List<ListStoryItem>>
        get() = _storyListLiveData

    fun getStoryFromViewModel(token: String) {
        val token = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = repository.getStoryFromRepository(token)

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
        if (modelClass.isAssignableFrom(MainViewModelMVVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModelMVVM(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}