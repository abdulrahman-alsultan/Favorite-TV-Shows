package com.example.abdulrahmanAlsultan.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abdulrahmanAlsultan.MainActivity
import com.example.abdulrahmanAlsultan.R
import com.example.abdulrahmanAlsultan.db.ShowsDatabase
import com.example.abdulrahmanAlsultan.db.TVShows
import com.example.abdulrahmanAlsultan.fragments.APIBrowser
import kotlinx.android.synthetic.main.api_item_row.view.*
import kotlinx.android.synthetic.main.local_item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class APIRecyclerViewAdapter(private val app: Context?): RecyclerView.Adapter<APIRecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var tvShows: MutableList<TVShows> = mutableListOf()

    private val database by lazy { ShowsDatabase.getInstance(app!!).tvShowsDao() }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.api_item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val show = tvShows[position]

        holder.itemView.apply {
            tv_apiRV_name.text = show.name

            tv_apiRV_language.text = show.language
            tv_apiRV_rate.text = show.rating
            if(show.rating == "null") tv_apiRV_rate.visibility = View.GONE

            if(show.premiered == "null") tv_apiRV_date.visibility = View.GONE
            if(show.ended != "null" ) tv_apiRV_date.text = "${show.premiered} - ${show.ended}"
            else tv_apiRV_date.text = show.premiered

            if(show.image.isNotEmpty()){
                try {
                    Glide.with(holder.itemView.context).load(show.image).into(tv_apiRV_image)
                }catch (e: Exception){
                    tv_apiRV_image.setImageDrawable(resources.getDrawable(R.drawable.default_image))
                }
            }

            iv_apiRV_download.setOnClickListener {
                CoroutineScope(IO).launch {
                    database.addShow(show)
                }
            }

        }
    }

    override fun getItemCount(): Int = tvShows.size


    fun update(newList: MutableList<TVShows>){
        this.tvShows = newList
        notifyDataSetChanged()
    }
}