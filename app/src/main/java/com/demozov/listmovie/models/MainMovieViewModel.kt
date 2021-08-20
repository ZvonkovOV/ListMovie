package com.demozov.listmovie.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demozov.listmovie.network.LANGUAGE_EN
import com.demozov.listmovie.network.MovieApi
import com.demozov.listmovie.network.MovieApiService
import com.demozov.listmovie.pojo.ListMovie
import kotlinx.coroutines.launch

class MainMovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<ListMovie>()
    val movies: MutableLiveData<ListMovie> = _movies

    fun getMovies(page: Int) {
        viewModelScope.launch {
            _movies.value = MovieApi.retrofitService.getMovies(LANGUAGE_EN, page)
        }
    }
}