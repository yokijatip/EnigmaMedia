package com.enigma.enigmamedia.view.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.enigma.enigmamedia.R
import com.enigma.enigmamedia.data.remote.api.ApiService
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import com.enigma.enigmamedia.databinding.ActivityRegisterBinding
import com.enigma.enigmamedia.view.login.LoginActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)


        val apiService = Client.getApiService()

        registerBinding.apply {
            tvLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }

//            Logic Password kalo kurang dari 8
            edtPassword.doOnTextChanged { text, start, before, count ->

                if (text!!.length < 8) {
                    edtPasswordLayout.error = "Harus 8 Karakter"
                } else {
                    edtPasswordLayout.error = null
                }

            }

            btnLogin.setOnClickListener {

                val name = edtUsername.text.toString()
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                if (name.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
                    toast("Form Harus di Isi Semua")
                } else if (password.length < 8) {
                    toast("Password Harus lebih dari 9")
                } else {
                    register(name, email, password)
                    finish()
                }

            }

//
        }

    }

    private val apiService = Client.getApiService()

    //    Fungsi Menangani Register
    private fun register(name: String, email: String, password: String) {
        val call = apiService.register(name, email, password)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Gagal Membuat Account",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //    Fungsi Toast
    private fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}