package com.example.esemkastore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkastore.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object{
        var Url = "http://10.0.2.2:5000"
        lateinit var IdUser :String
        lateinit var Name :String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.editTextTextEmailAddress.setText("khuddle0@cbc.ca")
        Binding.editTextTextPassword.setText("P@ssw0rd123")

        Binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                val Login = URL("$Url/api/Login").openConnection() as HttpURLConnection
                Login.requestMethod = "POST"
                Login.setRequestProperty("Content-Type","application/json")

                val Json = JSONObject().apply {
                    put("email",Binding.editTextTextEmailAddress.text)
                    put("password",Binding.editTextTextPassword.text)
//                    {
//                        "email": "khuddle0@cbc.ca",
//                        "password": "P@ssw0rd123"
//                    }
                }

                Login.outputStream.write(Json.toString().toByteArray())
                val Cek = Login.responseCode
                runOnUiThread {
                    if(Cek in 200 .. 299){
                        val User = JSONObject(Login.inputStream.bufferedReader().readText())
                        IdUser= User.getString("id")
                        Name = User.getString("name")
                        startActivity(Intent(this@MainActivity,MainActivity2::class.java))
                    }else{
                        Toast.makeText(this@MainActivity, "Login filed, please check your input", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}