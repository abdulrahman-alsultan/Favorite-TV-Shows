package com.example.abdulrahmanAlsultan.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abdulrahmanAlsultan.R
import com.example.abdulrahmanAlsultan.adapter.LocalDBRecyclerViewAdapter
import com.example.abdulrahmanAlsultan.db.TVShows
import com.example.abdulrahmanAlsultan.model.MyViewModel
import kotlinx.android.synthetic.main.fragment_local_database.view.*
import kotlinx.android.synthetic.main.local_item_row.*

class LocalDatabase : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var adapter: LocalDBRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_local_database, container, false)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getTVShow().observe(viewLifecycleOwner, {
                tvShows -> adapter.update(tvShows)
        })

        adapter = LocalDBRecyclerViewAdapter(viewModel)
        view.rv_local.adapter = adapter
        view.rv_local.layoutManager = LinearLayoutManager(activity)


        return view
    }

    fun delete(tvShows: TVShows){
        viewModel.deleteTVShow(tvShows)
    }

}