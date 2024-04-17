package com.example.esemkarestaurant

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkarestaurant.databinding.ActivityMain2Binding
import com.example.esemkarestaurant.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        val Fragment = listOf(MenuFragment(), CartFragment(), OrderFragment())
        val Menu = listOf("Menu", "Cart", "Orders")
        Binding.Vp.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = Menu.size

            override fun createFragment(position: Int): Fragment = Fragment[position]

        }

        TabLayoutMediator(Binding.Tl, Binding.Vp) { tab, pos ->
            tab.text = Menu[pos]
        }.attach()
    }
}