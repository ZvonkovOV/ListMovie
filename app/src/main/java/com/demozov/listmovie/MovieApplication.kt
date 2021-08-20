package com.demozov.listmovie

import android.app.Application
import com.demozov.listmovie.database.MovieRoomDatabase

class MovieApplication : Application() {
    val database: MovieRoomDatabase by lazy { MovieRoomDatabase.getDatabase(this) }
}