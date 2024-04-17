package com.example.esemkabakery

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkabakery.databinding.ActivityMain4Binding
import com.example.esemkabakery.databinding.CardCakeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

class MainActivity4 : AppCompatActivity() {
    companion object{
        lateinit var idD:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val Cake =
                URL("${MainActivity.Link}/api/Cake").openStream().bufferedReader().readText()
            val cakeArray = JSONArray(Cake)

            runOnUiThread {
                val adapter = object : RecyclerView.Adapter<CakeCardView>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): CakeCardView {
                        val binding = CardCakeBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )

                        return CakeCardView(binding)
                    }

                    override fun onBindViewHolder(holder: CakeCardView, position: Int) {
                        val data = cakeArray.getJSONObject(position)
                        //                        "cakeID": 0,
//                        "name": "string",
//                        "price": 0,
//                        "imageURL": "string"
                        holder.binding.textView14.text = data.getString("name")
                        GlobalScope.launch(Dispatchers.IO){

                        val imgUrl = data.getString("imageURL")
                        val BitImg = BitmapFactory.decodeStream(URL(imgUrl).openStream())
                        runOnUiThread {
                            holder.binding.imageView.setImageBitmap(BitImg)
                        }
                        }
                        holder.binding.cakeOpen.setOnClickListener {
                            idD = data.getString("cakeID")
                            startActivity(Intent(this@MainActivity4,MainActivity5::class.java))
                        }
                    }

                    override fun getItemCount(): Int = cakeArray.length()

                }

                Binding.RcV.adapter = adapter
                Binding.RcV.layoutManager =
                    GridLayoutManager(this@MainActivity4, 2, RecyclerView.VERTICAL, false)
            }
        }

        Binding.button5.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val sorce = Binding.editTextText2.text ?: ""
                val Cake =
                    URL("${MainActivity.Link}/api/Cake?search=$sorce").openStream().bufferedReader().readText()
                val cakeArray = JSONArray(Cake)

                runOnUiThread {
                    val adapter = object : RecyclerView.Adapter<CakeCardView>() {
                        override fun onCreateViewHolder(
                            parent: ViewGroup,
                            viewType: Int
                        ): CakeCardView {
                            val binding = CardCakeBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )

                            return CakeCardView(binding)
                        }

                        override fun onBindViewHolder(holder: CakeCardView, position: Int) {
                            val data = cakeArray.getJSONObject(position)
                            //                        "cakeID": 0,
//                        "name": "string",
//                        "price": 0,
//                        "imageURL": "string"
                            holder.binding.textView14.text = data.getString("name")
                            GlobalScope.launch(Dispatchers.IO){

                                val imgUrl = data.getString("imageURL")
                                val BitImg = BitmapFactory.decodeStream(URL(imgUrl).openStream())
                                runOnUiThread {
                                    holder.binding.imageView.setImageBitmap(BitImg)
                                }
                            }
                            holder.binding.cakeOpen.setOnClickListener {
                                idD = data.getString("cakeID")
                                startActivity(Intent(this@MainActivity4,MainActivity5::class.java))
                            }
                        }

                        override fun getItemCount(): Int = cakeArray.length()

                    }

                    Binding.RcV.adapter = adapter
                    Binding.RcV.layoutManager =
                        GridLayoutManager(this@MainActivity4, 2, RecyclerView.VERTICAL, false)
                }
            }
        }
    }

    class CakeCardView(val binding: CardCakeBinding) : RecyclerView.ViewHolder(binding.root)
}

