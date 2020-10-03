package com.scores.moviedbapp.utilities

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.scores.moviedbapp.R

class ImageLoaderMgr {
    companion object{
        private const val DAY_IN_HOURS: Int = 24 * 60 * 60 * 1000
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        fun showGif(splashIv: ImageView) {
            try {
                Glide.with(Utils.getContext())
                    .load("https://media.giphy.com/media/8lKyuiFprZaj2lC3WN/giphy.gif")
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(splashIv)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun loadMovieImg(imageView: ImageView, path: String){
            Glide.with(Utils.getContext())
                .load(BASE_IMAGE_URL+path)
                .placeholder(R.drawable.ic_baseline_star)
                .signature(ObjectKey(System.currentTimeMillis() / (DAY_IN_HOURS)))
                .into(imageView)
            Log.d("ImageLoaded", "Loaded from the following url: $BASE_IMAGE_URL$path")
        }
    }
}