package com.demozov.listmovie.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "list_favourite")
data class FavouriteMovie(
    @Json(name = "id") @ColumnInfo(name = "id") @PrimaryKey val id: Int,
    @Json(name = "release_date") @ColumnInfo(name = "release_date") val releaseDate: Long,
    @Json(name = "add_date") @ColumnInfo(name = "add_date") val addDate: Long
)
