package com.example.abdulrahmanAlsultan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.abdulrahmanAlsultan.R
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.btn_home_browsApi).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_APIBrowser)
        }

        view.findViewById<Button>(R.id.btn_home_localDB).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_localDatabase)
        }

        return view
    }

}