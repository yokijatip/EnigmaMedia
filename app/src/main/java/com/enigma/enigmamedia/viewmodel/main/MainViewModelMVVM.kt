package com.enigma.enigmamedia.viewmodel.main

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enigma.enigmamedia.data.remote.response.ListStoryItem
import com.enigma.enigmamedia.di.Injection
import com.enigma.enigmamedia.repository.Repository

class MainViewModelMVVM(private val repository: Repository) : ViewModel() {
    fun getStoryPagingFromViewModel(token: String): LiveData<PagingData<ListStoryItem>> {
        val tokenBearer = "Bearer $token"
        return repository.getStoryPagingSource(tokenBearer).cachedIn(viewModelScope).asLiveData()
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