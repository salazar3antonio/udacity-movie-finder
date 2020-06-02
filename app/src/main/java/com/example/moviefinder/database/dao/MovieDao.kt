package com.example.moviefinder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.database.model.Theater

@Dao
interface MovieDao {

    @Query("SELECT * FROM all_movies_table ORDER BY releaseDate ASC")
    fun getAllNowPlayingMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM all_movies_table WHERE isWantToWatch = 1")
    fun getAllWantToWatchMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("DELETE FROM all_movies_table")
    fun deleteAllMovies()

    @Update
    fun updateMovie(movie: Movie)

}