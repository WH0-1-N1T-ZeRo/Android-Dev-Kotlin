package com.example.esemkabakery

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkabakery.databinding.ActivityMain2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.textView4.setOnClickListener {
            finish()
        }
        Binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                val reg = URL("${MainActivity.Link}/api/Auth/Register").openConnection() as HttpURLConnection
                reg.requestMethod = "POST"
                reg.setRequestProperty("Content-Type","application/json")

                val Json = JSONObject().apply {
                    put("firstName",Binding.firstName.text)
                    put("lastName",Binding.lastName.text)
                    put("username",Binding.User.text)
                    put("email",Binding.editTextTextEmailAddress.text)
                    put("password",Binding.editTextTextPassword.text)
                    put("passwordConfirmation",Binding.editTextTextPassword2.text)
                }
//                "passwordConfirmation": "emma123",
//                "userID": 0,
//                "email": "emma@example.com",
//                "username": "emmabrownie",
//                "password": "emma123",
//                "firstName": "Emma",
//                "lastName": "Brown"
                val Out = DataOutputStream(reg.outputStream)
                Out.write(Json.toString().toByteArray())
                val Con = reg.responseCode

                runOnUiThread {
                    if(Con == 200 ){
                        Toast.makeText(this@MainActivity2, "Succes Register Account", Toast.LENGTH_SHORT).show()
                        finish()
                    }else Toast.makeText(this@MainActivity2, "Register not Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}