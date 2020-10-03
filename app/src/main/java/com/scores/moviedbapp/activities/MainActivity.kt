package com.scores.moviedbapp.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.scores.moviedbapp.R
import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.fragments.MovieDetailsPage
import com.scores.moviedbapp.fragments.MovieListPage
import com.scores.moviedbapp.interfaces.INavigateToNextScreen
import com.scores.moviedbapp.interfaces.IOpenLikedPage
import com.scores.moviedbapp.utilities.NetworkMgr

class MainActivity : AppCompatActivity(), INavigateToNextScreen, IOpenLikedPage{

    private lateinit var rootView : ConstraintLayout
    private lateinit var frameLayout : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
        rootView = findViewById(R.id.main_activity_cl)
        frameLayout = findViewById(R.id.main_frame_layout)
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(frameLayout.id, MovieListPage.newInstance()).commit()
    }

    companion object {
        private const val DETAILS: String = "DetailsPage"
    }

    override fun onBackPressed() {
        try {
            if(supportFragmentManager.backStackEntryCount < 1){
                finish()
            }
            else {
                super.onBackPressed()
            }
        } catch (e: Exception) {
            super.onBackPressed()
            e.printStackTrace()
        }
    }

    override fun onStop() {
        try {
            super.onStop()
            NetworkMgr.stopAllRequests()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRootView(): View {
        return rootView
    }

    override fun navigateToNextScreenWithTransision(data: Movie?, imageView: ImageView?) {
        if(imageView != null && ViewCompat.getTransitionName(imageView) != null){
            supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(imageView, ViewCompat.getTransitionName(imageView)!!)
//                  .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(frameLayout.id, MovieDetailsPage.newInstance(data, imageView.transitionName))
                    .addToBackStack(DETAILS)
                    .commit()
        }
        else{
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(frameLayout.id, MovieDetailsPage.newInstance(data, null))
                    .addToBackStack(DETAILS)
                    .commit()
        }
    }

    override fun refreshPage() {
        try {
            val listPage: Fragment = supportFragmentManager.findFragmentById(frameLayout.id)!!
            if (listPage is MovieListPage) {
                listPage.refreshListData()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun openLikedPage() {
        try {
            supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(frameLayout.id, MovieListPage.newInstanceLiked())
                .addToBackStack(DETAILS)
                .commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}