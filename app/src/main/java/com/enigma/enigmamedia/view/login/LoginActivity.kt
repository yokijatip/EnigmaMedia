package com.enigma.enigmamedia.view.login

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.LoginResponse
import com.enigma.enigmamedia.data.remote.response.LoginResult
import com.enigma.enigmamedia.databinding.ActivityLoginBinding
import com.enigma.enigmamedia.view.main.MainActivity
import com.enigma.enigmamedia.view.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)


//        Buat Ngasih Underline di text Forgot Password
        loginBinding.tvForgotPassword.paintFlags =
            loginBinding.tvForgotPassword.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        loginBinding.apply {
            tvForgotPassword.setOnClickListener {
                toast("wkwk makanya inget, fiturnya belom ada btw")
            }

            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
//            Logic Password Kalo kurang dari 8
            edtPassword.doOnTextChanged { text, start, before, count ->

                if (text!!.length < 8) {
                    edtPasswordLayout.error = "Harus 8 Karakter"
                } else {
                    edtPasswordLayout.error = null
                }

            }

            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                    toast("Form Harus Lengkap")
                } else if (password.length < 8) {
                    toast("Password harus lebih dari 8")
                } else {
                    showLoading(true)
                    loginUser(email, password)
                }

            }


        }


    }

    private val apiService = Client.getApiService()

    //    Fungsi Menangani Login
    private fun loginUser(email: String, password: String) {
        val call = apiService.login(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.loginResult?.token
                    val userId = response.body()?.loginResult?.userId
                    showLoading(false)

//                    Untuk Menyimpan data sesi dan token di preferences
                    val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
                    val manager = sharedPreferences.edit()
                    manager.putString("userId", userId)
                    manager.putString("token", token)
                    manager.apply()

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    toast("Gagal Login")
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast("Error")
            }
        })
    }

    private fun showLoading(state: Boolean) {
        loginBinding.loadingLogin.visibility = if (state) View.VISIBLE else View.GONE
    }

    //    Toast
    private fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}