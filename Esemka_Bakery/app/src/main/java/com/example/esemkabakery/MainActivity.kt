package com.example.esemkabakery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkabakery.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object {
        var Link = "http://10.0.2.2:5000"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.textView4.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
        }
        Binding.editTextTextEmailAddress.setText("john_doe")
        Binding.editTextTextPassword.setText("password")

        Binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                val login = URL("$Link/api/Auth/Login").openConnection() as HttpURLConnection
                login.requestMethod = "POST"
                login.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("usernameOrEmail", Binding.editTextTextEmailAddress.text)
                    put("password", Binding.editTextTextPassword.text)
                }
//                "usernameOrEmail": "john_doe",
//                "password": "password"
                val Out = DataOutputStream(login.outputStream)
                Out.write(Json.toString().toByteArray())

                val Con = login.responseCode
                runOnUiThread {
                    if (Con == 200) {
                        startActivity(Intent(this@MainActivity, MainActivity3::class.java))
                    }else Toast.makeText(this@MainActivity, "Login Error", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}