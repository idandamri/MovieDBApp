package com.scores.moviedbapp.fragments

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.scores.moviedbapp.R
import com.scores.moviedbapp.dbUtils.Movie
import com.scores.moviedbapp.interfaces.IonRequestCompleted
import com.scores.moviedbapp.objects.MoviesListResponseObject
import com.scores.moviedbapp.utilities.ImageLoaderMgr
import com.scores.moviedbapp.utilities.NetworkMgr
import com.scores.moviedbapp.utilities.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

class MovieDetailsPage : Fragment(), View.OnClickListener, IonRequestCompleted {

    private lateinit var yearTv: TextView
    private lateinit var screenshotIv: ImageView
    private lateinit var mainIv: ImageView
    private lateinit var backIv: ImageView
    private lateinit var titleTv: TextView
    private lateinit var overviewTv: TextView
    private lateinit var ratingTv: TextView

    companion object {
        private const val DATA_TAG: String = "movie_data"
        private const val TRANSITION_NAME: String = "transition_name"

        fun newInstance(data: Movie?, transitionName: String?): MovieDetailsPage {
            val fragment = MovieDetailsPage()
            val args = Bundle()
            if (data != null) {
                args.putSerializable(DATA_TAG, data)
                if (transitionName != null) {
                    args.putString(TRANSITION_NAME, transitionName)
                }
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            mainIv.transitionName = arguments?.getString(TRANSITION_NAME)
            setDataToViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(Utils.getContext())
            .inflateTransition(R.transition.default_transition)
        enterTransition = TransitionInflater.from(Utils.getContext())
            .inflateTransition(android.R.transition.no_transition)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_details_page_layout_2, container, false)
        initViews(view)
        val value = arguments?.getSerializable(DATA_TAG) as Movie
        ImageLoaderMgr.loadMovieImg(mainIv, value.posterPath)
        ImageLoaderMgr.loadMovieImg(screenshotIv, value.backdropPath)
        mainIv.layoutParams.height = Utils.deviceWidth*3/2
        return view
    }

    private fun setDataToViews() {
        try {
            val value = arguments?.getSerializable(DATA_TAG) as Movie
            titleTv.text = value.title
            overviewTv.text = value.overview
            val sb = StringBuilder()
            sb.append(Utils.getContext().resources.getString(R.string.rating_is))
            sb.append(" ")
            if (value.voteAverage != null) {
                sb.append(value.voteAverage.toString())
            }
            ratingTv.text = sb.toString()
            yearTv.text = value.releaseDate
            val genreTitleTv = TextView(Utils.getContext())
            sb.setLength(0)
            sb.append(" ")
            genreTitleTv.text = sb.toString()
            genreTitleTv.setTextColor(Utils.getContext().resources.getColor(R.color.mainTextColor, Utils.getContext().resources.newTheme()))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initViews(view: View?) {
        try {
            mainIv = view?.findViewById(R.id.details_iv)!!
            screenshotIv = view.findViewById(R.id.details_iv_screenshot)!!
            titleTv = view.findViewById(R.id.details_title_tv)
            overviewTv = view.findViewById(R.id.details_desc_tv)
            ratingTv = view.findViewById(R.id.details_rating_tv)
            yearTv = view.findViewById(R.id.details_year_tv)
            backIv = view.findViewById(R.id.details_back_iv)!!
            backIv.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        try {
            if(v?.id == R.id.details_back_iv){
                activity?.onBackPressed()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun navigateToMainActivity() {}

    override fun parseJsonString(jsonStr: String) {
        val data = NetworkMgr.getGson().fromJson(jsonStr, MoviesListResponseObject::class.java)
        Utils.setMovieList(data.results.toCollection(ArrayList()))
    }
}