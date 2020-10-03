package com.scores.moviedbapp.objects

import com.google.gson.annotations.SerializedName
import com.scores.moviedbapp.dbUtils.Movie
import java.io.Serializable

class MoviesListResponseObject(): Serializable {

    @SerializedName("results")
    lateinit var results: Array<Movie>
    @SerializedName("page")
    var page: Int = 1
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 1
}