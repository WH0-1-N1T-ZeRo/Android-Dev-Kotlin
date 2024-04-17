package com.example.esemkabakery

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkabakery.databinding.ActivityMain3Binding
import com.example.esemkabakery.databinding.CardCakeBinding
import kotlinx.coroutines.CoroutineExceptionHandler
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

        Binding.button4.setOnClickListener {
            startActivity(Intent(this@MainActivity3,MainActivity4::class.java))
        }
        GlobalScope.launch(Dispatchers.IO) {
            val cake = URL("${MainActivity.Link}/api/Cake").openStream().bufferedReader().readText()
            val cakeArray = JSONArray(cake)

            runOnUiThread {
                val adapter = object : RecyclerView.Adapter<CakeView>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeView {
                        val binding =
                            CardCakeBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                        return CakeView(binding)
                    }

                    override fun getItemCount(): Int = cakeArray.length()

                    override fun onBindViewHolder(holder: CakeView, position: Int) {
                        val data = cakeArray.getJSONObject(position)

                        holder.binding.textView14.text = data.getString("name")
                        GlobalScope.launch(Dispatchers.IO) {

                            val imgUrl = data.getString("imageURL")
                            val BitImg = BitmapFactory.decodeStream(URL(imgUrl).openStream())
                            runOnUiThread {
                                holder.binding.imageView.setImageBitmap(BitImg)
                            }
                        }
//                        "cakeID": 0,
//                        "name": "string",
//                        "price": 0,
//                        "imageURL": "string"
                        holder.binding.cakeOpen.setOnClickListener {
                            GlobalScope.launch(Dispatchers.IO) {

                                val Cake =
                                    URL("${MainActivity.Link}/api/Cake/${data.getString("cakeID")}").openStream()
                                        .bufferedReader().readText()
//                                Log.d("TAG", "onBindViewHolder: $Cake")

                                val Json = JSONObject(Cake)
                                val imgUrl = Json.getString("imageURL")
                                val BitImg = BitmapFactory.decodeStream(URL(imgUrl).openStream())
                                runOnUiThread {

                                    Binding.textView11.text = Json.getString("name")
                                    Binding.textView12.text = Json.getString("description")
                                    Binding.textView13.text = "$${Json.getString("price")}"
                                    Binding.imageView2.setImageBitmap(BitImg)
                                }
                                Binding.button2.setOnClickListener {
                                    MainActivity6.idOrder = arrayOf(data.getString("cakeID"))
                                    startActivity(Intent(this@MainActivity3,MainActivity6::class.java))
                                }
                            }

                        }
                    }

                }
                Binding.RcV.adapter = adapter
                Binding.RcV.layoutManager = LinearLayoutManager(this@MainActivity3,LinearLayoutManager.HORIZONTAL,false)
            }
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.Frame1,CakeFragment())
//                .commit()
        }

    }

    class CakeView(val binding: CardCakeBinding) : RecyclerView.ViewHolder(binding.root)
}

