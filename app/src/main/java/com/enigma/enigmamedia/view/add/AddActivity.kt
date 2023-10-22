package com.enigma.enigmamedia.view.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.enigma.enigmamedia.databinding.ActivityAddBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.utils.getImageUri
import com.enigma.enigmamedia.utils.uriToFile
import com.enigma.enigmamedia.view.landing.SplashScreenActivity
import com.enigma.enigmamedia.view.main.MainActivity
import id.zelory.compressor.Compressor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var addBinding: ActivityAddBinding

    private val addViewModel: AddViewModel by viewModels()

    private val tokenPreferences by lazy { TokenPreferences(this) }

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)


        addBinding.apply {
            icBack.setOnClickListener {
                startActivity(Intent(this@AddActivity, MainActivity::class.java))
                finish()
            }

//            Button buat buka gallery
            btnGallery.setOnClickListener {
                startGallery()
            }

            btnCamera.setOnClickListener {
                startCamera()
            }

            btnUpload.setOnClickListener {
                lifecycleScope.launch {
                    uploadToServerNoCoroutine()
                    val intent = Intent(this@AddActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    //    Fungsi buat start gallery
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    //    Fungsi buat buka gallery
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    //    Fungsi buat nampilin gambar
    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            addBinding.ivPhoto.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }


    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private suspend fun uploadToServer() {
        if (currentImageUri != null) {
            val token = getToken()
            val imageFile = uriToFile(currentImageUri!!, this)
            val description = addBinding.edtDescription.text.toString()

            val compressImage = imageFile?.let { Compressor.compress(this, it) }

            if (compressImage != null) {
                addViewModel.upload(token, compressImage, description, { message ->
                    showToast("Upload Berhasil: $message")
                }, { errorMessage ->
                    showToast("Upload Gagal: $errorMessage")
                })
            }
        } else {
            showToast("Pilih Photo")
        }
    }

    private suspend fun uploadToServerNoCoroutine(){
        if(currentImageUri != null) {
            val token = getToken()
            val imageFile = uriToFile(currentImageUri!!, this)
            val description = addBinding.edtDescription.text.toString()

            val compress = imageFile?.let { Compressor.compress(this,it)}

            if (compress != null) {
                addViewModel.uploadNotCoroutine(token, compress, description)
            }
        } else {
            showToast("Pilih photo")
        }
    }

    private suspend fun getToken(): String {
        return tokenPreferences.getToken().first()
    }

    private fun showLoading(isLoading: Boolean) {
        addBinding.loadingAdd.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}