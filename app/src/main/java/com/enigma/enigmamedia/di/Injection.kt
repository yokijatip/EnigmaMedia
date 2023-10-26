package com.enigma.enigmamedia.di

import android.content.Context
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = Client.getApiService()
        return Repository(apiService)
    }

}