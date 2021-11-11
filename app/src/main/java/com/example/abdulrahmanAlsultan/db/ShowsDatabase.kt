package com.example.abdulrahmanAlsultan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [TVShows::class],
    version = 1
)
abstract class ShowsDatabase: RoomDatabase(){

    abstract fun tvShowsDao(): TVShowsDao

    companion object{
        var instance: ShowsDatabase? = null
        fun getInstance(ctx: Context): ShowsDatabase{
            if(instance != null)
                return instance as ShowsDatabase
            instance = Room.databaseBuilder(ctx, ShowsDatabase::class.java, "TVShowInfo").fallbackToDestructiveMigration().build()
            return instance as ShowsDatabase
        }
    }


}