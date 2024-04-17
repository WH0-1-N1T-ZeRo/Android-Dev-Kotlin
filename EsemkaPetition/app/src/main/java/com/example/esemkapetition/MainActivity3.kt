package com.example.esemkapetition

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkapetition.databinding.ActivityMain3Binding
import com.example.esemkapetition.databinding.CardPetitionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(Binding.root)
        setSupportActionBar(Binding.mainTools)

        GlobalScope.launch(Dispatchers.IO) {
            val Url = URL("http://10.0.2.2:5000/petition").openStream().bufferedReader().readText()
            val ArrayUrl = JSONArray(Url)

            runOnUiThread {
                try {

                    var adapter = object : RecyclerView.Adapter<PetitionView>() {
                        override fun onCreateViewHolder(
                            parent: ViewGroup,
                            viewType: Int
                        ): PetitionView {
                            val binding = CardPetitionBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                            return PetitionView(binding)
                        }

                        override fun getItemCount(): Int = ArrayUrl.length()

                        override fun onBindViewHolder(holder: PetitionView, position: Int) {
                            var Out = ArrayUrl.getJSONObject(position)
                            holder.binding.textView10.text = Out.getString("title")
                            holder.binding.textView11.text = Out.getString("creatorName")
                            holder.binding.textView12.text = Out.getString("description")
//                        "title": "string",
//                        "description": "string",
//                        "target": 200,
//                        "creatorName": "string string","title": "string",
//                        "description": "string",
//                        "target": 200,
//                        "creatorName": "string string",

                            holder.binding.CardClick.setOnClickListener {
                                startActivity(
                                    Intent(
                                        this@MainActivity3,
                                        MainActivity6::class.java
                                    ).putExtra("Id", Out.getString("petitionID"))
                                )
                            }
                        }

                    }
                    adapter.notifyDataSetChanged()
                    Binding.RcV.adapter = adapter
                    Binding.RcV.layoutManager = LinearLayoutManager(this@MainActivity3)
                } catch (ex: Exception) {
                    Toast.makeText(this@MainActivity3, "Error petition", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    runOnUiThread {

        when (item.itemId){
            R.id.new_p-> startActivity(Intent(this@MainActivity3,MainActivity5::class.java))
            R.id.profile-> startActivity(Intent(this@MainActivity3,MainActivity4::class.java))
            R.id.log_out -> finish()
        }
    }
        return super.onOptionsItemSelected(item)
    }

    class PetitionView(val binding: CardPetitionBinding) : RecyclerView.ViewHolder(binding.root)
}

