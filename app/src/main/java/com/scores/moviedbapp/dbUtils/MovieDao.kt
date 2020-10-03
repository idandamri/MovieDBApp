package com.scores.moviedbapp.dbUtils

import androidx.room.*

@Dao
interface MovieDao {
    @Query("Select * from movies")
    fun getAllMovies(): List<Movie>

    @Query("Select id from movies")
    fun getLikedMoviesId(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)

//    @Query("SELECT * FROM movies WHERE (title == :title and release_date == :releaseYear)")
//    fun isMovieInDb(title: String, releaseYear: String): Array<Movie>


    @Query("SELECT * FROM movies WHERE (id == :id)")
    fun isMovieInDb(id: Int): Array<Movie>

}