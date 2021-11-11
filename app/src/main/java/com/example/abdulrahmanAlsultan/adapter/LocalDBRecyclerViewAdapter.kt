package com.example.abdulrahmanAlsultan.adapter

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abdulrahmanAlsultan.R
import com.example.abdulrahmanAlsultan.db.TVShows
import com.example.abdulrahmanAlsultan.fragments.APIBrowser
import com.example.abdulrahmanAlsultan.model.MyViewModel
import kotlinx.android.synthetic.main.api_item_row.view.*
import kotlinx.android.synthetic.main.local_item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class LocalDBRecyclerViewAdapter(private val myView: MyViewModel) :
    RecyclerView.Adapter<LocalDBRecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var tvShows: List<TVShows> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.local_item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val show = tvShows[position]

        holder.itemView.apply {
            tv_localRV_name.text = show.name
            tv_localRV_language.text = show.language
            tv_localRV_rate.text = show.rating
            if(show.premiered == "null") tv_localRV_date.visibility = View.GONE
            if(show.ended != "null" ) tv_localRV_date.text = "${show.premiered} - ${show.ended}"
            else tv_localRV_date.text = show.premiered

            if(show.image.isNotEmpty()){
                try {
                    Glide.with(holder.itemView.context).load(show.image).into(tv_localRV_image)
                }catch (e: Exception){
                    tv_localRV_image.setImageDrawable(resources.getDrawable(R.drawable.default_image))
                }
            }

            iv_localRV_delete.setOnClickListener {
                CoroutineScope(IO).launch {
                    myView.deleteTVShow(show)
                }
            }

        }
    }

    override fun getItemCount(): Int = tvShows.size


    fun update(newList: List<TVShows>) {
        this.tvShows = newList
        notifyDataSetChanged()
    }
}