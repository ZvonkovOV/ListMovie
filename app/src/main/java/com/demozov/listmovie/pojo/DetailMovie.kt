package com.demozov.listmovie.pojo

import com.squareup.moshi.Json

data class DetailMovie(
    @Json(name = "adult") var adult : Boolean?,
    @Json(name = "backdrop_path") var backdropPath : String?,
    @Json(name = "belongs_to_collection") var belongsToCollection : Any?,
    @Json(name = "budget") var budget : Int?,
    @Json(name = "genres") var genres : List<Genres>?,
    @Json(name = "homepage") var homepage : String?,
    @Json(name = "id") var id : Int,
    @Json(name = "imdb_id") var imdbId : String?,
    @Json(name = "original_language") var originalLanguage : String?,
    @Json(name = "original_title") var originalTitle : String?,
    @Json(name = "overview") var overview : String = "Coming Soon",
    @Json(name = "popularity") var popularity : Double = 0.0,
    @Json(name = "poster_path") var posterPath : String,
    @Json(name = "production_companies") var productionCompanies : List<ProductionCompanies>?,
    @Json(name = "production_countries") var productionCountries : List<ProductionCountries>?,
    @Json(name = "release_date") var releaseDate : String = "unknown",
    @Json(name = "revenue") var revenue : Int?,
    @Json(name = "runtime") var runtime : Int?,
    @Json(name = "spoken_languages") var spokenLanguages : List<SpokenLanguages>?,
    @Json(name = "status") var status : String?,
    @Json(name = "tagline") var tagline : String?,
    @Json(name = "title") var title : String,
    @Json(name = "video") var video : Boolean?,
    @Json(name = "vote_average") var voteAverage : Double = 0.0,
    @Json(name = "vote_count") var voteCount : Int = 0
)

data class Genres (
    @Json(name = "id") var id : Int?,
    @Json(name = "name") var name : String?
)

data class ProductionCompanies (
    @Json(name = "id") var id : Int?,
    @Json(name = "logo_path") var logoPath : String?,
    @Json(name = "name") var name : String?,
    @Json(name = "origin_country") var originCountry : String?
)

data class ProductionCountries (
    @Json(name = "iso_3166_1") var iso31661 : String?,
    @Json(name = "name") var name : String?
)

data class SpokenLanguages (
    @Json(name = "english_name") var englishName : String?,
    @Json(name = "iso_639_1") var iso6391 : String?,
    @Json(name = "name") var name : String?
)
