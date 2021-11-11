package com.example.abdulrahmanAlsultan.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TVShowsDao {
    @Query("SELECT * FROM tv_shows")
    fun getTVShows(): LiveData<List<TVShows>>

    @Insert
    fun addShow(tvShows: TVShows)

    @Delete
    fun deleteShow(tvShows: TVShows)
}