package com.scores.moviedbapp.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scores.moviedbapp.R
import com.scores.moviedbapp.utilities.Utils


open class BaseListFragment  : Fragment(){
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var viewAdapter: RecyclerView.Adapter<*>
    protected lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var pbLoader: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        initViews(view)
        handleAdapterWithData()
        sharedElementReturnTransition = TransitionInflater.from(Utils.getContext())
            .inflateTransition(R.transition.default_transition)
        exitTransition = TransitionInflater.from(Utils.getContext())
            .inflateTransition(android.R.transition.no_transition)
        return view
    }

    @Override
    open fun initViews(view: View) {
        try {
            pbLoader = view.findViewById(getPbLoaderId())
            showLoader()
            recyclerView = view.findViewById(R.id.movie_list_page_rv)
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
            setAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Override
    open fun showLoader() {
        pbLoader.visibility = View.VISIBLE
    }

    @Override
    open fun hideLoader() {
        pbLoader.visibility = View.GONE
    }

    @Override
    open fun getPbLoaderId(): Int {
        return R.id.pbLoader
    }

    @Override
    open fun setAdapter() {
    }

    @Override
    open fun getLayoutId(): Int {
        return -1
    }

    @Override
    open fun handleAdapterWithData() {
    }
}