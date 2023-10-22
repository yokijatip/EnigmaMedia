package com.enigma.enigmamedia.view.add

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.AddResponse
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("NAME_SHADOWING")
class AddViewModel : ViewModel() {

    fun upload(
        token: String,
        imageFile: File,
        description: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {

//                Token
                val token = "Bearer $token"

//                Request Body Deskripsi
                val reqDescription = description.toRequestBody("text/plain".toMediaType())

//                Request body photo
                val reqImage = imageFile.asRequestBody("image/*".toMediaType())
                val imagePart = MultipartBody.Part.createFormData("photo", imageFile.name, reqImage)

//                Call retrofit function
                val response = Client.getApiService().addNewStory(token, imagePart, reqDescription)

                if (response.isSuccessful) {
                    val message = response.body()?.message
                    message?.let {
                        onSuccess(it)
                    } ?: run {
                        onError("Response Kosong")
                    }
                } else {
                    val error = response.errorBody()?.string()
                    onError(error ?: "Error Response")
                }

            } catch (
                e: Exception
            ) {
                onError("Network Error: ${e.message}")
            }
        }

    }

    fun uploadNotCoroutine(

        token: String,
        imageFile: File,
        description: String

    ){
        val token = "Bearer $token"

        //                Request Body Deskripsi
        val reqDescription = description.toRequestBody("text/plain".toMediaType())

//                Request body photo
        val reqImage = imageFile.asRequestBody("image/*".toMediaType())
        val imagePart = MultipartBody.Part.createFormData("photo", imageFile.name, reqImage)

        Client.getApiService()
            .addNewStoryNoCoroutine(token, imagePart, reqDescription)
            .enqueue(object : Callback<AddResponse>{
                override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                    if (response.isSuccessful) {
                        Log.d("Success", "Upload Berhasil")
                    }
                }

                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    Log.d("Failed", "Upload Gagal")
                }



            })

    }



}