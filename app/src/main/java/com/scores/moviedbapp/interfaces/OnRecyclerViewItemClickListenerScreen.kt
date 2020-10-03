package com.scores.moviedbapp.interfaces

import android.widget.ImageView
import com.scores.moviedbapp.dbUtils.Movie

interface OnRecyclerViewItemClickListenerScreen {
    fun onRecyclerViewItemClicked(data: Movie, imageView: ImageView)
}
