package com.scores.moviedbapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.scores.moviedbapp.R
import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.interfaces.OnRecyclerViewItemClickListenerScreen
import com.scores.moviedbapp.utilities.ImageLoaderMgr
import com.scores.moviedbapp.utilities.Utils
import kotlinx.android.synthetic.main.movie_list_item_layout.view.*
import java.lang.ref.WeakReference


class ListPageAdapter (private var onRecyclerViewItemClickListenerScreen : OnRecyclerViewItemClickListenerScreen):
    RecyclerView.Adapter<ListPageAdapter.MovieViewHolder>() {

    private var showLikeToggleBtn: Boolean = true
    private var items: List<Movie> = ArrayList()

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun getItem(position: Int): Movie {
        return items[position]
    }

    fun getItemsList(): List<Movie> {
        return items
    }

    fun setListItems(itemsList: List<Movie>){
        items = itemsList
    }

    class MovieViewHolder(
        itemView: View,
        private var onRecyclerViewItemClickListenerScreen: OnRecyclerViewItemClickListenerScreen,
        var showLikeToggleBtn: Boolean
    )
        : RecyclerView.ViewHolder(itemView){

        fun bind(value: Movie, position: Int) {
            try {
                itemView.movie_item_name_tv.text = value.title
                itemView.movie_item_release_date_tv.text = value.releaseDate

                ImageLoaderMgr.loadMovieImg(itemView.movie_item_iv, value.posterPath)
                itemView.movie_item_iv.transitionName = "transition$position"
                itemView.movie_item_like_button_selected.isClickable = false
                if (showLikeToggleBtn) {
                    if (value.isLiked) {
                        itemView.movie_item_like_button_selected.visibility = View.VISIBLE
                    } else {
                        itemView.movie_item_like_button_selected.visibility = View.INVISIBLE
                    }
                }
                else {
                    itemView.movie_item_like_button.visibility = View.GONE
                    itemView.movie_item_like_button_selected.visibility = View.GONE
                }
                if (!itemView.movie_item_like_button.hasOnClickListeners()) {
                    itemView.movie_item_like_button.setOnClickListener(LikeBtnListener(value, position, this))
                }
                itemView.setOnClickListener {
                    onRecyclerViewItemClickListenerScreen.onRecyclerViewItemClicked(value, itemView.movie_item_iv as ImageView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object{
        class LikeBtnListener(var movie: Movie, var position: Int, movieViewHolder: MovieViewHolder) : View.OnClickListener{
            var holderRef: WeakReference<MovieViewHolder> = WeakReference(movieViewHolder)

            override fun onClick(v: View?) {
                try {
                    if(holderRef.get() != null){
                        val holder = holderRef.get() as MovieViewHolder
                        movie.isLiked = !movie.isLiked
                        if (movie.isLiked) {
                            Utils.addMovieToDb(movie)
                        }
                        else {
                            Utils.removeMovieFromDb(movie)
                        }
                        holder.itemView.movie_item_like_button_selected.visibility = if(movie.isLiked){
                            View.VISIBLE
                        }
                        else {
                            View.INVISIBLE
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item_layout, parent, false)
        return MovieViewHolder(
            view,
            onRecyclerViewItemClickListenerScreen,
            showLikeToggleBtn
        )
    }

    fun setShowLikeToggleButton(value: Boolean) {
        showLikeToggleBtn = value
    }
}