package com.scores.moviedbapp.utilities

import com.scores.moviedbapp.R
import com.scores.moviedbapp.activities.MainActivity
import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.dbUtils.MovieDao
import com.scores.moviedbapp.dbUtils.MoviesDatabase

class DbManager {

    companion object{

        var dao: MovieDao? = null
        var db: MoviesDatabase? = null

        fun addMovieToDb(data: Movie) {
            try {
                db = MoviesDatabase.getDataBase()
                dao = db?.movieDao()
                db?.movieDao()?.insertMovie(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun removeMovieFromDb(data: Movie) {
            try {
                db = MoviesDatabase.getDataBase()
                dao = db?.movieDao()
                if (db?.movieDao()?.isMovieInDb(data.id)!!.isNotEmpty()) {
                    db?.movieDao()?.deleteMovie(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getSetOfIds(): HashSet<Int> {
            var ret = HashSet<Int>()
            try {
                db = MoviesDatabase.getDataBase()
                dao = db?.movieDao()
//                val listOfAllMovies = db?.movieDao()?.getAllMovies()
                val listOfLikedMovieIds = db?.movieDao()?.getLikedMoviesId()
//                if(listOfAllMovies != null && listOfAllMovies.isNotEmpty()){
//                    for(movie in listOfAllMovies){
//                        ret.add(movie.id)
//                    }
//                }
                ret = listOfLikedMovieIds?.toSet()?.toHashSet()!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ret
        }
    }
}