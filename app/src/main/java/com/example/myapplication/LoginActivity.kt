package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ui.main.CommunicationService
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val inputUsername = findViewById(R.id.username) as EditText
        val inputPassword = findViewById(R.id.password) as EditText

        val loginBtn = findViewById(R.id.login_btn) as Button

        loginBtn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://concordia-hack.appspot.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(CommunicationService::class.java)
            val request = service.getUserConfirmation(inputUsername.text.toString(), inputPassword.text.toString())

            request.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val intent = Intent(this@LoginActivity, AppActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "KO" + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        spotify_connect.setOnClickListener {
            SpotifyService.connect(this) {
                // val intent = Intent(this, PlayerActivity::class.java)
                // startActivity(intent)
                Log.println(Log.INFO, "importantYo", "Worked as planned")
            }
        }

    }

}
