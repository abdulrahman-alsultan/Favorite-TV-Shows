package com.example.abdulrahmanAlsultan.db

import androidx.lifecycle.LiveData

class Repository(private val tvShowsDao: TVShowsDao) {

    val getShows: LiveData<List<TVShows>> = tvShowsDao.getTVShows()

    suspend fun add(tvShows: TVShows){
        tvShowsDao.addShow(tvShows)
    }

    suspend fun delete(tvShows: TVShows){
        tvShowsDao.deleteShow(tvShows)
    }

}