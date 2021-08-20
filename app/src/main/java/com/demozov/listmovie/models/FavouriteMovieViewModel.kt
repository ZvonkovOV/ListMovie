package com.demozov.listmovie.models

import androidx.lifecycle.*
import com.demozov.listmovie.database.MovieDao
import com.demozov.listmovie.network.LANGUAGE_EN
import com.demozov.listmovie.network.LANGUAGE_RU
import com.demozov.listmovie.network.MovieApi
import com.demozov.listmovie.pojo.DetailMovie
import com.demozov.listmovie.pojo.FavouriteMovie
import kotlinx.coroutines.launch
import retrofit2.Call

class FavouriteMovieViewModel(private val movieDao: MovieDao) : ViewModel() {

    private var _itemMovie = MutableLiveData<DetailMovie>()
    val itemMovie: LiveData<DetailMovie> = _itemMovie

    private var _allMovies = MutableLiveData<List<FavouriteMovie>>()
    val allMovies: LiveData<List<FavouriteMovie>> = _allMovies

    fun getAllMovies() {
        viewModelScope.launch {
            _allMovies.value = movieDao.getMovies()
        }
    }

    class FavouriteMovieViewModelFactory(private val movieDao: MovieDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteMovieViewModel::class.java)) {
                @Suppress
                return FavouriteMovieViewModel(movieDao) as T
            }
            throw IllegalArgumentException("Unknown VIewModel Class")
        }
    }

    fun getMovie(id: Int) {
        viewModelScope.launch {
            _itemMovie.value = MovieApi.retrofitService.getMovie(id, LANGUAGE_EN)
        }
    }
}