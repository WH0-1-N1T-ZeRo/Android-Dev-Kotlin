package com.example.esemkabakery

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkabakery.databinding.ActivityMain5Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val CakeDetile = URL("${MainActivity.Link}/api/Cake/${MainActivity4.idD}").openStream()
                .bufferedReader().readText()

            //                        "cakeID": 0,
//                        "name": "string",
//                        "price": 0,
//                        "imageURL": "string"
            val Json = JSONObject(CakeDetile)
            Binding.textView11.text = Json.getString("name")
            Binding.textView12.text = Json.getString("description")
            Binding.textView13.text = Json.getString("price")

            val Url = BitmapFactory.decodeStream(URL(Json.getString("imageURL")).openStream())

            runOnUiThread {
                Binding.imageView2.setImageBitmap(Url)
            }
            Binding.button2.setOnClickListener {
                getSharedPreferences("",Context.MODE_PRIVATE)
                MainActivity6.idOrder = arrayOf(Json.getString("cakeID"))
                startActivity(Intent(this@MainActivity5, MainActivity6::class.java))
            }
        }
    }
}