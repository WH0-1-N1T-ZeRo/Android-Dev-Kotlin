package com.example.esemkabakery

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.esemkabakery.databinding.ActivityMain6Binding

class MainActivity6 : AppCompatActivity() {
    companion object{
        lateinit var idOrder :Array<String>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain6Binding.inflate(layoutInflater)
        setContentView(Binding.root)
        idOrder.forEach { element ->
            Log.d("ArrayElement", element)
        }
    }
}