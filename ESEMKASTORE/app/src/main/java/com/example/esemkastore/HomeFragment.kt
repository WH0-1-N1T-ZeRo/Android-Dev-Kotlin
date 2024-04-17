package com.example.esemkastore

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.databinding.CardHomeBinding
import com.example.esemkastore.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {
companion object{
    lateinit var IdItem:String
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val Binding = FragmentHomeBinding.inflate(layoutInflater)
        GlobalScope.launch(Dispatchers.IO){
            val Home = URL("${MainActivity.Url}/api/Home/Item").openStream().bufferedReader().readText()
            val ArrayH = JSONArray(Home)

            Binding.textView7.setText("Welcome "+MainActivity.Name)
            GlobalScope.launch(Dispatchers.Main){
                val adapter = object : RecyclerView.Adapter<HomeView>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeView {
                        val binding = CardHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                        return  HomeView(binding)
                    }

                    override fun onBindViewHolder(holder: HomeView, position: Int) {
                        val data = ArrayH.getJSONObject(position)
                        holder.binding.textView3.text = data.getString("name")
                        holder.binding.textView4.text = data.getString("description")

                        val imageUrl = "${MainActivity.Url}/api/Home/Item/Photo/${data.getString("id")}"
                        val defaultImageResource = R.drawable.filed_img // Ganti dengan sumber gambar default Anda

                        GlobalScope.launch(Dispatchers.IO) {
                            var bitmap: Bitmap? = null
                            try {
                                bitmap = BitmapFactory.decodeStream(URL(imageUrl).openStream())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            GlobalScope.launch(Dispatchers.Main) {
                                if (bitmap != null) {
                                    holder.binding.imageView.setImageBitmap(bitmap)
                                } else {
                                    holder.binding.imageView.setImageResource(defaultImageResource)
                                }
                            }
                        }
                        holder.binding.CardH.setOnClickListener {
                            IdItem = data.getString("id")
                            startActivity(Intent(requireContext(),MainActivity3::class.java))
                        }

                    }
//                    "id": 1,
//                    "name": "Peanut",
//                    "description": "Introducing our premium quality peanuts – the perfect snack for any occasion! Our peanuts are carefully selected from the finest crops and roasted to perfection to bring out their natural sweetness and nutty flavor. With their high protein, fiber, and healthy fat content, our peanuts are not only delicious but also a nutritious addition to your diet.",
//                    "price": 40000,
//                    "stock": 100
                    override fun getItemCount(): Int = ArrayH.length()

                }

                Binding.RcV.adapter =adapter
                Binding.RcV.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
            }
        }
        return Binding.root
    }

}

class HomeView (val binding: CardHomeBinding):RecyclerView.ViewHolder(binding.root)
