package com.scores.moviedbapp.dbUtils

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.Exception


@Entity(tableName = "movies", primaryKeys = ["title", "release_date"])
class Movie : Serializable {

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String = ""

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String = ""

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double? = null

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String = ""

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String = ""

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    var overview: String = ""

    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int = -1

    @ColumnInfo(name = "liked")
    var isLiked: Boolean = false

//    @SerializedName("genre")
//    @TypeConverters(ListConverter::class)
//    @ColumnInfo(name = "genreString")
//    var genre: List<String>? = null


    fun getYear(): Int {
        try {
            return releaseDate.toInt()
        } catch (e: Exception) {
            return -1
        }
    }
}