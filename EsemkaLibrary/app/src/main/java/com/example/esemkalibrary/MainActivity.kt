package com.example.esemkalibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkalibrary.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object{
        var url = "http://10.0.2.2:5000"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)
//                POST /Api/Auth
//                {
//                    "email": "iclarricoates3@clickbank.net",
//                    "password": "fTa9aI71rEm"
//                }
        Binding.editTextText.setText("iclarricoates3@clickbank.net")
        Binding.editTextTextPassword.setText("fTa9aI71rEm")
//        var token =
//            getSharedPreferences("JWTHandling", Context.MODE_PRIVATE).getString("token", null)
//        if (token != null) {
//            startActivity(Intent(this@MainActivity, MainActivity3::class.java)).apply {
//                var flags =
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            }
//        }
        Binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val Login = URL("$url/Api/Auth").openConnection() as HttpURLConnection
                Login.requestMethod = "POST"
                Login.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("email", Binding.editTextText.text)
                    put("password", Binding.editTextTextPassword.text)
                }

                Login.outputStream.write(Json.toString().toByteArray())
                val Out = Login.responseCode
                runOnUiThread {
                    try {
                        if (Out == 200) {
                            val JsonO = JSONObject(Login.inputStream.bufferedReader().readText())
//                            getSharedPreferences("JWTHandling", Context.MODE_PRIVATE).edit().apply {
//                                remove("token")
//                            }.apply()
                            getSharedPreferences("JWTHandling", Context.MODE_PRIVATE).edit().apply {
                                putString("token", JsonO.getString("token"))
                            }.apply()

                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MainActivity3::class.java
                                )
                            ).apply {
                                var flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        }
                    } catch (ex: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Login Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}