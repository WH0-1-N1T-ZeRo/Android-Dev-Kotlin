package com.example.esemkapetition

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkapetition.databinding.ActivityMain5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        Binding.button3.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val Input =
                    URL("http://10.0.2.2:5000/petition").openConnection() as HttpURLConnection
                Input.requestMethod = "POST"
                Input.setRequestProperty("Content-Type", "application/json")

                val Json = JSONObject().apply {
                    put("title", Binding.titleInp.text)
                    put("description", Binding.editTextTextMultiLine.text)
                    put("target", Binding.editTextNumberSigned.text)
                    put("creatorID", MainActivity.MyID ?: "1")
                }
                val Output = DataOutputStream(Input.outputStream)
                Output.write(Json.toString().toByteArray())

//                "title": "string",
//                "description": "string",
//                "target": 2147483647,
//                "creatorID": 1
                val Respon = Input.responseCode
                runOnUiThread {
                    if (Respon == 201) {
                        Toast.makeText(this@MainActivity5, "Input Success", Toast.LENGTH_SHORT)
                            .show()
                        Binding.titleInp.setText("")
                        Binding.editTextNumberSigned.setText("")
                        Binding.editTextTextMultiLine.setText("")
                    } else Toast.makeText(this@MainActivity5, "Input Not Foud", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this@MainActivity5, MainActivity3::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}