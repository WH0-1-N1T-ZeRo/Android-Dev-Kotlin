package com.example.esemkalibrary

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkalibrary.databinding.ActivityMain2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.button3.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                if(Binding.editTextTextPassword3.text != Binding.editTextTextPassword3.text){
                    runOnUiThread {
                        Toast.makeText(this@MainActivity2, "Password confirmation error", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                val SingUp = URL("${MainActivity.url}/Api/Users").openConnection() as HttpURLConnection
                SingUp.requestMethod = "POST"
                SingUp.setRequestProperty("Content-Type","application/json")

                val Json = JSONObject().apply {
                    put("name",Binding.editTextText2.text)
                    put("password",Binding.editTextTextPassword3.text)
                    put("email",Binding.editTextTextEmailAddress.text)
                }
//                "name": "string",
//                "password": "string",
//                "email": "user@example.com"
                SingUp.outputStream.write(Json.toString().toByteArray())
                val Out = SingUp.responseCode
                runOnUiThread {
                try {
                    if (Out == 200){
                        Toast.makeText(this@MainActivity2, "Sing Up Succec", Toast.LENGTH_SHORT).show()
                    finish()
                    }

                }catch (ex:Exception){
                    Toast.makeText(this@MainActivity2, "Sing Up Filed", Toast.LENGTH_SHORT).show()
                }
                }
            }
        }
    }
}