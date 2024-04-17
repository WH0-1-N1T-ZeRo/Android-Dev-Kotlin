package com.example.esemkalibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkalibrary.databinding.ActivityMain3Binding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val Jwt = URL("${MainActivity.url}/Api/Book").openConnection() as HttpURLConnection
            var token =
                getSharedPreferences("JWTHandling", Context.MODE_PRIVATE).getString("token", null)
            if (token != null) {
                Jwt.setRequestProperty("Authorization", "Bearer $token")
            }
            val out = Jwt.responseCode
            Log.d("TAG", "onCreate: Pepek error $token")
            try {

            if (out !in 200..299) {
                if (out == 401) {
                    startActivity(Intent(this@MainActivity3, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    getSharedPreferences("JWTHandling", Context.MODE_PRIVATE).edit().apply {
                        remove("token")
                    }.apply()
                } else {
                    var errorMsg = Jwt.errorStream.bufferedReader().readText()
                    runOnUiThread {
                        Toast.makeText(this@MainActivity3, "Jwt Pepek : $token", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                val Json = JSONObject(Jwt.inputStream.bufferedReader().readText())
                Log.d("TAG", "onCreate: ${Json.getString("name")}")
            }
            }catch (ex:Exception){
                Log.d("TAG", "onCreate: Kenapa ${ex.message}")
            }
        }
        val Fragment = listOf( ForumFragment(),HomeFragment(), CartFragment(), ProfileFragment())
        val Menu = listOf("Menu", "Forum", "My Cart", "My Profile")

        Binding.Vp.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = Fragment.size

            override fun createFragment(position: Int): Fragment = Fragment[position]

        }

        TabLayoutMediator(Binding.Tl, Binding.Vp) { tes, pos ->
            tes.text = Menu[pos]
        }.attach()
    }
}