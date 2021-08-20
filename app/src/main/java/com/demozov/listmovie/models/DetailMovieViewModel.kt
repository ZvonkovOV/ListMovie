package com.demozov.listmovie.models

import android.os.Build
import androidx.lifecycle.*
import com.demozov.listmovie.database.MovieDao
import com.demozov.listmovie.network.LANGUAGE_EN
import com.demozov.listmovie.network.MovieApi
import com.demozov.listmovie.pojo.DetailMovie
import com.demozov.listmovie.pojo.FavouriteMovie
import com.demozov.listmovie.pojo.Reviews
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.*
import java.util.Calendar.*

class DetailMovieViewModel(private val movieDao: MovieDao) : ViewModel() {

    private var _itemMovie = MutableLiveData<DetailMovie>()
    val itemMovie: LiveData<DetailMovie> = _itemMovie

    private var _listReviews = MutableLiveData<List<Reviews>>()
    val listReviews: LiveData<List<Reviews>> = _listReviews

    private var _allMovies = MutableLiveData<List<FavouriteMovie>>()
    val allMovies: LiveData<List<FavouriteMovie>> = _allMovies

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        viewModelScope.launch {
            _allMovies.value = movieDao.getMovies()
        }
    }

    fun getMovie(id: Int) {
        viewModelScope.launch {
            _itemMovie.value = MovieApi.retrofitService.getMovie(id, LANGUAGE_EN)
        }
    }

    fun getReviews(movieId: Int) {
        viewModelScope.launch {
            _listReviews.value =
                MovieApi.retrofitService.getReviews(movieId, LANGUAGE_EN, 1).results
        }
    }

    fun addMovie(id: Int) {
        viewModelScope.launch {
            movieDao.insert(
                FavouriteMovie(
                    id = id,
                    releaseDate = getDataRelease(),
                    addDate = getInstance().timeInMillis
                )
            )
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            movieDao.delete(id)
        }
    }

    private fun getDataRelease(): Long {
        val tmp = LocalDate.parse(itemMovie.value?.releaseDate ?: "1970-01-01")
        return tmp.toEpochDay()
    }
}

class DetailMovieViewModelFactory(private val movieDao: MovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailMovieViewModel::class.java)) {
            @Suppress
            return DetailMovieViewModel(movieDao) as T
        }
        throw IllegalArgumentException("Unknown VIewModel Class")
    }

}