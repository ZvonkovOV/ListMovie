package com.demozov.listmovie.pojo

import com.squareup.moshi.Json

data class ListReviews(
        @Json(name = "id") val id : Int,
        @Json(name = "page") val page : Int,
        @Json(name = "results") val results : List<Reviews>,
        @Json(name = "total_pages") val total_pages : Int,
        @Json(name = "total_results") val total_results : Int
)

data class Reviews(
        @Json(name = "author") val author : String?,
        @Json(name = "author_details") val author_details : AuthorDetails?,
        @Json(name = "content") val content : String?,
        @Json(name = "created_at") val created_at : String?,
        @Json(name = "id") val id : String?,
        @Json(name = "updated_at") val updated_at : String?,
        @Json(name = "url") val url : String?
)

data class AuthorDetails(
        @Json(name = "name") val name : String?,
        @Json(name = "username") val username : String?,
        @Json(name = "avatar_path") val avatar_path : String?,
        @Json(name = "rating") val rating : String?
)
