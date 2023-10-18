package com.enigma.enigmamedia.view.login

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.databinding.ActivityLoginBinding
import com.enigma.enigmamedia.utils.TokenPreferences
import com.enigma.enigmamedia.view.main.MainActivity
import com.enigma.enigmamedia.view.register.RegisterActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val tokenPreferences by lazy { TokenPreferences(this) }

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

                if (email.isEmpty() || password.isEmpty()) {
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

    val apiService = Client.getApiService()


    //    Fungsi Menangani Login
//    private fun loginUser(email: String, password: String) {
//        val call = apiService.login(email, password)
//        call.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful) {
//                    val token = response.body()?.loginResult?.token
//                    val userId = response.body()?.loginResult?.userId
//                    showLoading(false)
//
////                    Menyimpan Token ke Datastore
//                    dataStore.saveToken(token)
//
////                    Untuk Menyimpan data sesi dan token di Shared Preferences
//                    val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
//                    val manager = sharedPreferences.edit()
//                    manager.putString("userId", userId)
//                    manager.putString("token", token)
//                    manager.apply()
//
//
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    intent.putExtra("token", token)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    toast("Gagal Login")
//                    showLoading(false)
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                toast("Error")
//            }
//        })
//    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loginUser(email: String, password: String) {
        showLoading(true)
        val call = apiService.login(email, password)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val token = response.body()?.loginResult?.token
                    response.body()?.loginResult?.userId

                    // Simpan token ke Data Store
                    if (token != null) {
                        tokenPreferences.saveToken(token)
                    }

                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        toast("Gagal Login")
                        showLoading(false)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    toast("Error: ${e.message}")
                    showLoading(false)
                }
            }
        }
    }


    private fun showLoading(state: Boolean) {
        loginBinding.loadingLogin.visibility = if (state) View.VISIBLE else View.GONE
    }

    //    Toast
    private fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}