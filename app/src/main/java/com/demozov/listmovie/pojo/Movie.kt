package com.demozov.listmovie.pojo

import com.squareup.moshi.Json

data class Movie(
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "backdrop_path") val backdrop_path: String?,
    @Json(name = "genre_ids") val genre_ids: List<Int>?,
    @Json(name = "id") val id: Int,
    @Json(name = "original_language") val original_language: String?,
    @Json(name = "original_title") val original_title: String?,
    @Json(name = "overview") val overview: String = "Coming soon",
    @Json(name = "popularity") val popularity: Double = 0.0,
    @Json(name = "poster_path") val poster_path: String,
    @Json(name = "release_date") val release_date: String = "1970-01-01",
    @Json(name = "title") val title: String,
    @Json(name = "video") val video: Boolean?,
    @Json(name = "vote_average") val vote_average: Double = 0.0,
    @Json(name = "vote_count") val vote_count: Int = 0
)
