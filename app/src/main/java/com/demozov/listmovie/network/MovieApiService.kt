package com.demozov.listmovie.network

import com.demozov.listmovie.pojo.DetailMovie
import com.demozov.listmovie.pojo.ListMovie
import com.demozov.listmovie.pojo.ListReviews
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val BASE_PICTURE_URL = "https://image.tmdb.org/t/p/"
const val PICTURE_SIZE_185 = "w185"
const val PICTURE_SIZE_500 = "w500"
const val API_KEY = "1d935fbe70cae0b4a4e4f6b5e8a482a2"
const val LANGUAGE_EN = "en-US"
const val LANGUAGE_RU = "ru-RU"
const val SHARED_FAVOURITES = "favoritesMovies"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {
    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getMovies(@Query("language") language: String, @Query("page") page: Int):
            ListMovie

    @GET("movie/{movie_id}/reviews?api_key=$API_KEY")
    suspend fun getReviews(@Path("movie_id") movieId: Int,
                   @Query("language") language: String,
                   @Query("page") page: Int):
            ListReviews

    @GET("movie/{movie_id}?api_key=$API_KEY")
    suspend fun getMovie(@Path("movie_id") movieId: Int, @Query("language") language: String):
            DetailMovie
}

object MovieApi {
    val retrofitService : MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}
