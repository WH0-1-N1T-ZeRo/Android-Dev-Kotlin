package com.example.esemkapetition

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.esemkapetition.databinding.CardPetitionPBinding
import com.example.esemkapetition.databinding.FragmentMyBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL


class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentMyBinding.inflate(layoutInflater)
//        return inflater.inflate(R.layout.fragment_my, container, false)
//        &signedBy=1
        val view = Binding.root

        GlobalScope.launch(Dispatchers.IO) {
            val Petition = URL("http://10.0.2.2:5000/petition?createdBy=${MainActivity.MyID}").openStream().bufferedReader().readText()
            val PArray = JSONArray(Petition)

            GlobalScope.launch(Dispatchers.Main){
                val adapter = object : RecyclerView.Adapter<PetitionCardVIew>(){
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): PetitionCardVIew {
                        val binding = CardPetitionPBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                        return PetitionCardVIew(binding)
                    }

                    override fun getItemCount(): Int = PArray.length()

                    override fun onBindViewHolder(holder: PetitionCardVIew, position: Int) {
                        val data = PArray.getJSONObject(position)
                        holder.binding.textView10.text = data.getString("title")
                        holder.binding.textView11.text = data.getString("creatorName")
                        holder.binding.textView12.text = data.getString("description")
                    }

                }

                Binding.RcV.adapter = adapter
                Binding.RcV.layoutManager = LinearLayoutManager(this@MyFragment.context)
            }
        }
            return view

    }
class PetitionCardVIew ( val binding : CardPetitionPBinding) : RecyclerView.ViewHolder(binding.root)



}
