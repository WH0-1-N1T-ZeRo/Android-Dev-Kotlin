package com.example.esemkapetition

import android.content.Intent
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkapetition.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var MyID: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.button2.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                startActivity(Intent(this@MainActivity, MainActivity2::class.java))
            }
        }
        Binding.editTextTextEmailAddress.setText("user@example.com")
        Binding.editTextTextPassword.setText("string")
//        "email": "user@example.com",
//        "password": "string"
        Binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val Url = URL("http://10.0.2.2:5000/sign-in").openConnection() as HttpURLConnection
                Url.requestMethod = "POST"
                Url.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("email", Binding.editTextTextEmailAddress.text)
                    put("password", Binding.editTextTextPassword.text)
                }
                val Output = DataOutputStream(Url.outputStream)
                Output.write(Json.toString().toByteArray())
                val Respon = Url.responseCode
                runOnUiThread {
                    if (Respon == 200) {
                        val reObj = JSONObject(Url.inputStream.bufferedReader().readText())
                        MyID = reObj.getString("userID")
                        startActivity(Intent(this@MainActivity, MainActivity3::class.java))
                    } else {
                        Toast.makeText(this@MainActivity, "Login Filed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}