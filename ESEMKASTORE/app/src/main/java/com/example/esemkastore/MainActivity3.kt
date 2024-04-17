package com.example.esemkastore

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.databinding.ActivityMain3Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL


class MainActivity3 : AppCompatActivity() {
    private var Item = 1
    private var Price = 1.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(Binding.root)

        GlobalScope.launch(Dispatchers.IO) {
            val Detail =
                URL("${MainActivity.Url}/api/Home/Item").openStream().bufferedReader().readText()
            val ArrayH = JSONArray(Detail)

            for (i in 0 until ArrayH.length()) {
                val value = ArrayH.getJSONObject(i)
                if (value.getString("id") == HomeFragment.IdItem) {
                    Binding.textView5.text = value.getString("name")
                    Binding.toolbar.title = value.getString("name")
                    Binding.textView6.text = value.getString("description")
                    Binding.textView8.text = "Rp." + value.getString("price")
                    Binding.textView9.text = "Stock : " + value.getString("stock")

                    Price =  value.getString("price").toDouble()

                    //                    "id": 1,
//                    "name": "Peanut",
//                    "description": "Introducing our premium quality peanuts – the perfect snack for any occasion! Our peanuts are carefully selected from the finest crops and roasted to perfection to bring out their natural sweetness and nutty flavor. With their high protein, fiber, and healthy fat content, our peanuts are not only delicious but also a nutritious addition to your diet.",
//                    "price": 40000,
//                    "stock": 100

                    GlobalScope.launch(Dispatchers.IO) {
                        val imageUrl =
                            "${MainActivity.Url}/api/Home/Item/Photo/${value.getString("id")}"
                        val defaultImageResource =
                            R.drawable.filed_img // Ganti dengan sumber gambar default Anda
                        var bitmap: Bitmap? = null
                        try {
                            bitmap = BitmapFactory.decodeStream(URL(imageUrl).openStream())
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        GlobalScope.launch(Dispatchers.Main) {
                            if (bitmap != null) {
                                Binding.imageView2.setImageBitmap(bitmap)
                            } else {
                                Binding.imageView2.setImageResource(defaultImageResource)
                            }
                        }
                    }
                }
                Updateitem(Binding.editTextText, Binding.totalPrice)
                Binding.button2.setOnClickListener {
                    Item--
                    Updateitem(Binding.editTextText, Binding.totalPrice)
                }
                Binding.button3.setOnClickListener {
                    Item++
                    Updateitem(Binding.editTextText, Binding.totalPrice)
                }
                Binding.button4.setOnClickListener {
                    ArraySesion.Data.add(HomeFragment.IdItem)
                    ArraySesion.Count.add(Item.toString())
                    ArraySesion.Price.add((Item * Price).toString())

                    val intent = Intent(this@MainActivity3, MainActivity2::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)

                }
            }
        }
    }

    private fun Updateitem(textView: TextView, textView2: TextView) {
        textView.text = Item.toString()
        textView2.text = "Total Price : Rp."+(Item * Price).toString()
    }
}