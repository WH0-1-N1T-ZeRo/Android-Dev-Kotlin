package com.example.esemkastore

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkastore.databinding.FragmentCartBinding
import com.example.esemkastore.databinding.ListCardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.Duration
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.days

class CartFragment : Fragment() {
 lateinit var idS:String
 private var DurationS:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentCartBinding.inflate(layoutInflater)

        Binding.button5.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val Cart =
                    URL("${MainActivity.Url}/api/Checkout/Transaction").openConnection() as HttpURLConnection
                Cart.requestMethod = "POST"
                Cart.setRequestProperty("Content-Type", "application/json")

                val Json = JSONArray(ArraySesion.Price)

                val Json1 = JSONObject().apply {
                    put("userId", MainActivity.IdUser)
                    put("serviceId", idS)
                    var total = .0

                    ArraySesion.Price?.let {
                        val jsonArray = JSONArray(it)

                        for (i in 0 until jsonArray.length()) {
                            val value = jsonArray.getString(i).toDoubleOrNull() ?: .0
                            total += value
                        }
                    }
                    put("totalPrice", total)
                    put("orderDate", LocalDateTime.now())
                    put("acceptanceDate", LocalDateTime.now().plusDays(DurationS.toLong()))
                    val Leght = minOf(JSONArray(ArraySesion.Data).length(), JSONArray(ArraySesion.Count).length())
                    val ArrayJson = JSONArray()
                    for (a in 0 until Leght) {
                        val detailObject = JSONObject().apply {
                            put("itemId", JSONArray(ArraySesion.Data).getInt(a))
                            put("count", JSONArray(ArraySesion.Count).getInt(a))
                        }
                        ArrayJson.put( detailObject)
                    }
                        put("detail",ArrayJson)
                }
                        Log.d("TAG", "onCreateView: $Json1")
            }
//            startActivity(Intent(requireContext(), MainActivity2::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            })
        }

        GlobalScope.launch(Dispatchers.IO) {
            val Spiner = URL("${MainActivity.Url}/api/Checkout/Service").openStream().bufferedReader().use {
                it.readText()
            }
            val ArrayS = JSONArray(Spiner)

            GlobalScope.launch(Dispatchers.Main) {
                val spinnerItems = mutableListOf<String>()
                val spinnerId = mutableListOf<String>()
                val spinnerDuration = mutableListOf<String>()


                // Iterasi melalui array JSON dan tambahkan setiap item ke dalam list spinnerItems
                for (i in 0 until ArrayS.length()) {
                    val ObjectJson= ArrayS.getJSONObject(i)
                    spinnerItems.add("${ObjectJson.getString("name")}\n${ObjectJson.getString("price")} (${ObjectJson.getString("duration")}(s))")
//                    "id": 1,
//                    "name": "Fast Delivery",
//                    "duration": 2,
//                    "price": 12000,
                    spinnerId.add(ObjectJson.getString("id"))
                    spinnerDuration.add(ObjectJson.getString("duration"))
                }

                // Buat adapter untuk spinner menggunakan list spinnerItems
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)

                // Atur layout dropdown yang akan digunakan saat spinner di-klik
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                Binding.spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val Selectid = spinnerId[position]
                        val Duration = spinnerDuration[position]
                        idS = Selectid
                        DurationS = Duration.toInt()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
                // Set adapter ke dalam spinner menggunakan view binding
                Binding.spinner.adapter = adapter
            }
        }

        GlobalScope.launch(Dispatchers.IO){
val Item = URL("${MainActivity.Url}/api/Home/Item").openStream().bufferedReader().readText()
                val ArrayH = JSONArray(Item)
GlobalScope.launch(Dispatchers.Main) {

    val adapter = object :RecyclerView.Adapter<CartViewH>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewH {
            val  binding = ListCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return  CartViewH(binding)
        }

        override fun onBindViewHolder(holder: CartViewH, position: Int) {
            val data = ArrayH.getJSONObject(position)
            for (i in 0 until ArrayH.length()) {
                val value = ArrayH.getJSONObject(i)
                if (ArraySesion.Data.contains(value.getString("id"))) {
                    holder.binding.textView12.text = data.getString("name")
                    GlobalScope.launch(Dispatchers.IO) {
                        val IMG = BitmapFactory.decodeStream(
                            URL(
                                "${MainActivity.Url}/api/Home/Item/Photo/${
                                    value.getString("id")
                                }"
                            ).openStream()
                        )

                        GlobalScope.launch(Dispatchers.Main) {
                            holder.binding.imageView4.setImageBitmap(IMG)
                        }
                    }
                }
            }
        }

        override fun getItemCount(): Int = ArraySesion.Data.size

    }
    Binding.RcV.adapter = adapter
    Binding.RcV.layoutManager = LinearLayoutManager(requireContext())
}

        }
        return Binding.root

    }

}

class CartViewH (val binding: ListCardBinding):RecyclerView.ViewHolder(binding.root)
