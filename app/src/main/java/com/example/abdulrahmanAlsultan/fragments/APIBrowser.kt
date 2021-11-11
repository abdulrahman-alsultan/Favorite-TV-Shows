package com.example.abdulrahmanAlsultan.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abdulrahmanAlsultan.R
import com.example.abdulrahmanAlsultan.adapter.APIRecyclerViewAdapter
import com.example.abdulrahmanAlsultan.db.ShowsDatabase
import com.example.abdulrahmanAlsultan.db.TVShows
import kotlinx.android.synthetic.main.fragment_a_p_i_browser.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

class APIBrowser : Fragment() {

    private lateinit var adapter: APIRecyclerViewAdapter
    private lateinit var showsList: MutableList<TVShows>

    private val database by lazy { ShowsDatabase.getInstance(requireContext()).tvShowsDao() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_a_p_i_browser, container, false)

        showsList = mutableListOf()

        adapter = APIRecyclerViewAdapter(activity?.applicationContext)
        view.rv_fragment_api.adapter = adapter
        view.rv_fragment_api.layoutManager = LinearLayoutManager(activity)

        view.btn_apiRV_search.setOnClickListener {
            if(view.et_apiRV_search.text.isNotEmpty()){
                showsList.clear()
                setURL(view.et_apiRV_search.text.toString())
                adapter.update(showsList)
            }
        }
        return view
    }

    suspend fun add(tvShow: TVShows){
        database.addShow(tvShow)
    }

    private fun setURL(searchWord: String) {
        CoroutineScope(IO).launch {
            val response = try{
                URL("https://api.tvmaze.com/search/shows?q=$searchWord").readText(Charsets.UTF_8)
            }catch (e: Exception){
                ""
            }
            if(response.isNotEmpty()){
                fetchData(response)
            }
        }
    }

    private suspend fun fetchData(response: String) {
        withContext(Main){
            try {
                val json = JSONArray(response)
                for(i in 0 until json.length()){
                    val obj = json.getJSONObject(i)
                    val show = obj.getJSONObject("show")
                    val id = show.getInt("id")
                    val url = show.getString("url")
                    val name = show.getString("name")
                    val genres = show.getJSONArray("genres")
                    var genresString = ""
                    for(g in 0 until genres.length()){
                        genresString += "${genres.getString(g)}, "
                    }
                    val lang = show.getString("language")
                    val officialSite = show.getString("officialSite")

                    val premiered = show.getString("premiered")
                    val ended = show.getString("ended")
                    val image = try {
                        show.getJSONObject("image").getString("medium")
                    }catch (e: Exception){
                        ""
                    }
                    val rating = show.getJSONObject("rating").getString("average")
                    showsList.add(
                        TVShows(
                            id,
                            url,
                            name,
                            genresString,
                            lang,
                            officialSite,
                            "",
                            "",
                            rating,
                            image,
                            premiered,
                            ended
                        )
                    )

                }

            }catch (e: Exception){}
        }
    }



}

