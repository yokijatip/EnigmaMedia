package com.enigma.enigmamedia.view.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.enigma.enigmamedia.data.remote.client.Client
import com.enigma.enigmamedia.data.remote.response.RegisterResponse
import com.enigma.enigmamedia.databinding.ActivityRegisterBinding
import com.enigma.enigmamedia.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.apply {
            tvLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }

//            Logic Password kalo kurang dari 8
            edtPassword.doOnTextChanged { text, _, _, _ ->

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

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    toast("Form Harus di Isi Semua")
                } else if (password.length < 8) {
                    toast("Password Harus lebih dari 9")
                } else {
                    showLoading(true)
                    Handler().postDelayed({
                        register(name, email, password)
                    },1000)
                }
            }
        }
    }

    private val apiService = Client.getApiService()

    //    Fungsi Menangani Register
    private fun register(name: String, email: String, password: String) {
        apiService.register(name, email, password).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Silahkan Login😁👍", Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Gagal Membuat Account",
                        Toast.LENGTH_SHORT
                    ).show()
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        registerBinding.loadingRegister.visibility = if (state) View.VISIBLE else View.GONE
    }

    //    Fungsi Toast
    private fun toast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }
}