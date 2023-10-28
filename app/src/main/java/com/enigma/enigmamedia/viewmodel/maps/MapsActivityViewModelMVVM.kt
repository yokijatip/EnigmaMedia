package com.enigma.enigmamedia.viewmodel.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigma.enigmamedia.data.remote.response.UserLocation
import com.enigma.enigmamedia.repository.Repository
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MapsActivityViewModelMVVM(private val repository: Repository) : ViewModel() {

    private val storyDetailLiveData = MutableLiveData<UserLocation>()

    fun getUserLocation(token: String) {
        val token = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = repository.getUserLocation(token)

                if (response.isSuccessful) {
                    val lat = response.body()?.lat
                    val lon = response.body()?.lon
                    val name = response.body()?.name

                    if (lat != null && lon != null && name != null) {
                        storyDetailLiveData.value = UserLocation(name, lat, lon)
                    } else {
                        storyDetailLiveData.value = UserLocation("", 0.0, 0.0)
                    }
                } else {
                    println("Error")
                }

            } catch (e: Exception) {
                e.message
            }
        }
    }
}
