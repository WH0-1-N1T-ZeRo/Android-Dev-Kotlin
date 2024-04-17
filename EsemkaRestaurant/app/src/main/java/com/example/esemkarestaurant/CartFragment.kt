package com.example.esemkarestaurant

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.esemkarestaurant.databinding.FragmentCartBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentCartBinding.inflate(layoutInflater)

        Binding.button3.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val Cart = URL("${MainActivity.Url}/Api/Table/${MainActivity.id}/Order").openConnection() as HttpURLConnection
                Cart.requestMethod ="POST"
                Cart.setRequestProperty("Content-Type","application/json")

val JsonO = JSONObject().apply {
    put("","")
}
//                val JsonA = JSONArray().apply {
//                    put("",)
//                }
            }
        }
        return Binding.root
    }

}