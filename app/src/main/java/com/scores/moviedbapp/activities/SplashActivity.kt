package com.scores.moviedbapp.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.scores.moviedbapp.R
import com.scores.moviedbapp.interfaces.IonRequestCompleted
import com.scores.moviedbapp.objects.MoviesListResponseObject
import com.scores.moviedbapp.utilities.ImageLoaderMgr
import com.scores.moviedbapp.utilities.NetworkMgr
import com.scores.moviedbapp.utilities.Utils
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity(), IonRequestCompleted {

    companion object{
        private const val MOVIE_LIST: String = "movie_list"
    }

    private lateinit var splashIv : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setContent(applicationContext)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
        splashIv = findViewById(R.id.splash_iv)
        ImageLoaderMgr.showGif(splashIv)
        Utils.setDeviceMetrics(this)
        Utils.updateSetOfLikedMovieIds(this)
    }

    override fun onResume() {
        try {
            super.onResume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigateToMainActivity() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                delay(TimeUnit.SECONDS.toMillis(2))
                withContext(Dispatchers.Main) {
                    try {
                        navigateAfterAnimation()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun navigateAfterAnimation() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
             e.printStackTrace()
        }
    }

    override fun parseJsonString(jsonStr: String) {
        try {
            val data = NetworkMgr.getGson().fromJson(jsonStr, MoviesListResponseObject::class.java)
            Utils.setMovieList(data.results.toCollection(ArrayList()))
            navigateToMainActivity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}