package com.scores.moviedbapp.interfaces

import android.widget.ImageView
import com.scores.moviedbapp.dbUtils.Movie

interface INavigateToNextScreen {
    fun navigateToNextScreenWithTransision(data: Movie?, imageView: ImageView?)
    fun refreshPage()
}
