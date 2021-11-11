package com.example.abdulrahmanAlsultan.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.abdulrahmanAlsultan.db.Repository
import com.example.abdulrahmanAlsultan.db.ShowsDatabase
import com.example.abdulrahmanAlsultan.db.TVShows
import com.example.abdulrahmanAlsultan.db.TVShowsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository
    private val tvShows: LiveData<List<TVShows>>


    init {
        val dao = ShowsDatabase.getInstance(application).tvShowsDao()
        repository = Repository(dao)
        tvShows = repository.getShows
    }


    fun getTVShow(): LiveData<List<TVShows>> {
        return tvShows
    }

    fun deleteTVShow(tvShow: TVShows){
        CoroutineScope(IO).launch {
            repository.delete(tvShow)
        }
    }
}