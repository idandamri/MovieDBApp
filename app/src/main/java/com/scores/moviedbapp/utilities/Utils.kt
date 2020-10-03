package com.scores.moviedbapp.utilities

import android.content.Context
import android.util.DisplayMetrics
import com.scores.moviedbapp.activities.SplashActivity
import com.scores.moviedbapp.dbUtils.Movie
import kotlinx.coroutines.*

class Utils {

    companion object{

        var deviceHeight: Int = -1
        var deviceWidth: Int = -1
        private lateinit var contextInstance : Context

        fun getContext(): Context {
            return contextInstance
        }

        fun setContent(context: Context) {
            contextInstance = context
        }

        private fun convertDpToPixel(dp: Float): Float {
            val resources = getContext().resources
            val metrics = resources.displayMetrics
            return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun getMoviesList(): ArrayList<Movie>? {
            return listOfMovies
        }

        private var listOfMovies: ArrayList<Movie>? = null

        private var setOfLikedMovieIds: HashSet<Int>? = null

        fun updateSetOfLikedMovieIds(splashActivity: SplashActivity) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    setOfLikedMovieIds = DbManager.getSetOfIds()
                    NetworkMgr.request(splashActivity, NetworkMgr.MOVIES_URL + NetworkMgr.pageNum)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun addMovieToDb(movie: Movie) {
            try {
                setOfLikedMovieIds?.add(movie.id)
                CoroutineScope(Dispatchers.IO).launch {
                    DbManager.addMovieToDb(movie)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun removeMovieFromDb(movie: Movie) {
            try {
                setOfLikedMovieIds?.remove(movie.id)
                CoroutineScope(Dispatchers.IO).launch {
                    DbManager.removeMovieFromDb(movie)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun setMovieList(list: ArrayList<Movie>){
            try {
                if(listOfMovies.isNullOrEmpty()){
                    listOfMovies = ArrayList()
                }
                for (movie in list) {
                    if(setOfLikedMovieIds?.contains(movie.id)!!){
                        movie.isLiked = true
                    }
                }
                listOfMovies = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun setDeviceMetrics(context: Context){
            val displayMetrics: DisplayMetrics = context.resources.displayMetrics
            deviceWidth = displayMetrics.widthPixels
            deviceHeight = displayMetrics.heightPixels
        }

        fun getLikedMoviesList(): ArrayList<Movie>? {
            val ret = ArrayList<Movie>()
            try {
                for(movie in listOfMovies!!){
                    if(setOfLikedMovieIds?.contains(movie.id)!!){
                        ret.add(movie)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ret
        }
    }

}