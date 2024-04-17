package com.example.esemkapetition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkapetition.databinding.CardPetitionPBinding
import com.example.esemkapetition.databinding.FragmentSingnedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class SingnedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentSingnedBinding.inflate(layoutInflater)
        val view = Binding.root
        GlobalScope.launch(Dispatchers.IO) {
            val singed =
                URL("http://10.0.2.2:5000/petition?signedBy=${MainActivity.MyID}").openStream()
                    .bufferedReader().readText()
            val SingArray = JSONArray(singed)
            GlobalScope.launch(Dispatchers.Main) {
                val adapter = object : RecyclerView.Adapter<SingedView>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingedView {
                        val binding = CardPetitionPBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                        return  SingedView(binding)
                    }

                    override fun onBindViewHolder(holder: SingedView, position: Int) {
                        val data = SingArray.getJSONObject(position)
                        holder.binding.textView10.text = data.getString("title")
                        holder.binding.textView11.text = data.getString("creatorName")
                        holder.binding.textView12.text = data.getString("description")
                    }

                    override fun getItemCount(): Int = SingArray.length()

                }
                Binding.RcV.adapter = adapter
                Binding.RcV.layoutManager = LinearLayoutManager(this@SingnedFragment.context)
            }
        }
        return view
    }

    class SingedView(val binding: CardPetitionPBinding) : RecyclerView.ViewHolder(binding.root)
}

