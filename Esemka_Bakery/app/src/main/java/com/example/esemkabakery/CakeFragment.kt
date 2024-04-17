package com.example.esemkabakery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.esemkabakery.databinding.FragmentCakeBinding


class CakeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val Binding = FragmentCakeBinding.inflate(layoutInflater)
        val view = Binding.root
        return  view
    }


}