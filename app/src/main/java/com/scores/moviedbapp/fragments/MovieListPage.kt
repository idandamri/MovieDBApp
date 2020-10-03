package com.scores.moviedbapp.fragments

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.scores.moviedbapp.R
import com.scores.moviedbapp.activities.MainActivity
import com.scores.moviedbapp.adapters.ListPageAdapter
import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.interfaces.INavigateToNextScreen
import com.scores.moviedbapp.interfaces.OnRecyclerViewItemClickListenerScreen
import com.scores.moviedbapp.utilities.Utils
import java.util.*

class MovieListPage : BaseListFragment() , OnRecyclerViewItemClickListenerScreen,
    View.OnTouchListener {

    private lateinit var btnAdd: ImageView
    private var dX: Float = 0f
    private var dY: Float = 0f
    private var startClickTime: Long = 0

    companion object {
        fun newInstance(): MovieListPage {
            val fragment = MovieListPage()
            val args = Bundle()
            args.putBoolean(IS_LIKED_PAGE, false)
            fragment.arguments = args
            return fragment
        }

        fun newInstanceLiked(): MovieListPage {
            val fragment = MovieListPage()
            val args = Bundle()
            args.putBoolean(IS_LIKED_PAGE, true)
            fragment.arguments = args
            return fragment
        }

        private const val IS_LIKED_PAGE: String = "is_liked_page"
        const val MAX_CLICK_DURATION = 300
    }

    override fun setAdapter() {
        viewAdapter = ListPageAdapter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.movie_list_page_layout
    }

    override fun getPbLoaderId(): Int {
        return R.id.pbLoader
    }

    override fun initViews(view: View) {
        super.initViews(view)
        try {
            btnAdd = view.findViewById(R.id.open_liked_list)
            if (arguments?.getBoolean(IS_LIKED_PAGE, false)!!) {
                btnAdd.visibility = View.GONE
            }
            else {
                btnAdd.isClickable = true
                btnAdd.setOnTouchListener(this)
                (recyclerView.adapter as ListPageAdapter).setShowLikeToggleButton(true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            (recyclerView.adapter as ListPageAdapter)
                .setToggleButtonAccordingToPageType(arguments?.getBoolean(IS_LIKED_PAGE, false)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun handleAdapterWithData() {
        try {
            if (recyclerView.adapter == null) {
                recyclerView.adapter = viewAdapter
            }
            val adapter = viewAdapter as ListPageAdapter
            adapter.setListItems(Utils.getMovieListAccordingToType(arguments?.getBoolean(IS_LIKED_PAGE, false)!!))
            recyclerView.adapter?.notifyDataSetChanged()
            hideLoader()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRecyclerViewItemClicked(data: Movie, imageView: ImageView) {
        try {
            openMovieDetailsScreen(data, imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openMovieDetailsScreen(data: Movie, imageView: ImageView) {
        try {
            if(activity is INavigateToNextScreen){
                (activity as INavigateToNextScreen).navigateToNextScreenWithTransision(data, imageView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = v?.x!! - event.rawX
                dY = v.y - event.rawY
                startClickTime = Calendar.getInstance().timeInMillis
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration: Long =
                    Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) {
                    openLikedListFragment()
                }
            }
            MotionEvent.ACTION_MOVE ->
                v?.animate()?.x(event.rawX + dX)
                    ?.y(event.rawY + dY)
                    ?.setDuration(0)
                    ?.start()
            else -> return false
        }
        return true
    }

    private fun openLikedListFragment() {
        try {
            if(activity != null && activity is MainActivity){
                (activity as MainActivity).openLikedPage()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshListData() {
        try {
            (viewAdapter as ListPageAdapter).notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}