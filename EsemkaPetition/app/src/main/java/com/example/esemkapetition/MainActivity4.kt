package com.example.esemkapetition

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.esemkapetition.databinding.ActivityMain4Binding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val Id =
                URL("http://10.0.2.2:5000/user/ ${MainActivity.MyID}").openStream().bufferedReader()
                    .readText()

            val jsonObject = JSONObject(Id)
            Binding.textView16.text =
                " ${jsonObject.getString("firstName")} ${jsonObject.getString("lastName")}"
            Binding.textView17.text = jsonObject.getString("email")
        }
        var FragmentList = listOf(MyFragment(), SingnedFragment())
        var MenuList = listOf("My Petition", "Singned Petition")

        Binding.menuVp.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = FragmentList.size

            override fun createFragment(position: Int): Fragment = FragmentList[position]

        }

        TabLayoutMediator(Binding.menuTl, Binding.menuVp) { tab, pos ->
            tab.text = MenuList[pos]
        }.attach()
    }
}