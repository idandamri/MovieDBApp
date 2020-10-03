package com.scores.moviedbapp.interfaces

import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.utilities.NetworkMgr
import com.scores.moviedbapp.objects.MoviesListResponseObject

interface IonRequestCompleted {
    fun navigateToMainActivity()
    fun parseJsonString(jsonStr : String)
}

