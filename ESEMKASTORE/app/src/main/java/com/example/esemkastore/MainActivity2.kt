package com.example.esemkastore

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkastore.databinding.ActivityMain2Binding
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONArray

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        val Frame = listOf(HomeFragment(), CartFragment(), HistoryFragment())
        val Name = listOf("Home", "Card (${ArraySesion.Data.size})", "History")
        Binding.Vp.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = Frame.size

            override fun createFragment(position: Int): Fragment = Frame[position]
        }
        TabLayoutMediator(Binding.Tl, Binding.Vp) { tab, pos ->
            tab.text = Name[pos]
        }.attach()
    }
}