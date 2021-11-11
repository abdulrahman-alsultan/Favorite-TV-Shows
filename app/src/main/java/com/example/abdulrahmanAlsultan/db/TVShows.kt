package com.example.abdulrahmanAlsultan.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TVShows(
    @PrimaryKey
    val id: Int, //
    val url: String, //
    val name: String, //
    val genres: String, //
    val language: String, //
    val officialSite: String, //
    val time: String,
    val days: String,
    val rating: String, //
    val image: String, //
    val premiered: String, //
    val ended: String //
)