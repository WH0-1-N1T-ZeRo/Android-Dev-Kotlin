package com.example.esemkapetition

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkapetition.databinding.ActivityMain2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.button.setOnClickListener {
            finish()
        }
        Binding.button2.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val Url = URL("http://10.0.2.2:5000/sign-up").openConnection() as HttpURLConnection
                Url.requestMethod = "POST"
                Url.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("firstName", Binding.editTextText.text)
                    put("lastName", Binding.editTextText2.text)
                    put("email", Binding.editTextTextEmailAddress2.text)
                    put("password", Binding.editTextTextPassword2.text)
                }
                val OutPut = DataOutputStream(Url.outputStream)
                OutPut.write(Json.toString().toByteArray())
                val Con = Url.responseCode
                runOnUiThread {

                    if (Con == 201) {
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity2, "Error Sing Up $Con", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}