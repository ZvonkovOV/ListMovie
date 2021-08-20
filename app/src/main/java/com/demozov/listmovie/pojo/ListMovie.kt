package com.demozov.listmovie.pojo

import com.squareup.moshi.Json

data class ListMovie(
        @Json(name = "page") val page : Int,
        @Json(name = "results") val results : List<Movie>,
        @Json(name = "total_pages") val total_pages : Int,
        @Json(name = "total_results") val total_results : Int
)
