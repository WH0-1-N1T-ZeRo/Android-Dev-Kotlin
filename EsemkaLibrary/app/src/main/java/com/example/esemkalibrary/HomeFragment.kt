package com.example.esemkalibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkalibrary.databinding.FragmentHomeBinding
import com.example.esemkalibrary.databinding.HomeBookBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentHomeBinding.inflate(layoutInflater)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val Home =
                    URL("${MainActivity.url}/Api/Book").openStream().bufferedReader().readText()
                val ArrayH = JSONArray(Home)

                GlobalScope.launch(Dispatchers.Main) {
                    val adapter = object : RecyclerView.Adapter<HomeView>() {
                        override fun onCreateViewHolder(
                            parent: ViewGroup,
                            viewType: Int
                        ): HomeView {
                            val binding = HomeBookBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            )
                            return HomeView(binding)
                        }

                        override fun onBindViewHolder(holder: HomeView, position: Int) {
                            val data = ArrayH.getJSONObject(position)
                            holder.binding.textView10.text = data.getString("authors")
                            holder.binding.textView11.text = data.getString("name")
//                        "name": "Schiller, Jast and Dicki",
//                        "authors
                        }

                        override fun getItemCount(): Int = ArrayH.length()

                    }
                    Binding.RcV.adapter = adapter
                    Binding.RcV.layoutManager =
                        GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                }
            }catch (ex:Exception){
                GlobalScope.launch(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Anak kontol : ${ex.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return Binding.root
    }
}

class HomeView (val binding: HomeBookBinding):RecyclerView.ViewHolder(binding.root)
