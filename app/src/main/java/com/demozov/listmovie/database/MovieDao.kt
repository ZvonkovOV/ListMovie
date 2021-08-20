package com.demozov.listmovie.database

import androidx.room.*
import com.demozov.listmovie.pojo.FavouriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: FavouriteMovie)

    @Query(value = "DELETE FROM list_favourite WHERE id = :id")
    suspend fun delete(id: Int)

    @Query(value = "SELECT * FROM list_favourite ORDER BY add_date")
    suspend fun getMovies(): List<FavouriteMovie>
}