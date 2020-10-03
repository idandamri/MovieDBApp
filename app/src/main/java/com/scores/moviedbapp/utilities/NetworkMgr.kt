package com.scores.moviedbapp.utilities

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.scores.moviedbapp.interfaces.IonRequestCompleted

class NetworkMgr {
    companion object{
        private const val API_KEY = "2f3ecea5efd1e937fd0124a3f3df783c"
        const val MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY&language=en-US&page="
        var pageNum: Int = 1
        val NETWORK_TAG = "network_tag"
        val queue = Volley.newRequestQueue(Utils.getContext())
        private var gson: Gson = Gson()

        fun increasePageNum(){
            pageNum++
        }

        fun resetPageNum(){
            pageNum=1
        }

        fun request(requestSuccessfulListener: IonRequestCompleted, url : String) {
            try {
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        requestSuccessfulListener.parseJsonString(response)
                    },
                    {
                        Toast.makeText(Utils.getContext(),"Error in request", Toast.LENGTH_SHORT).show()
                    })
                stringRequest.tag = NETWORK_TAG
                queue.add(stringRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun stopAllRequests(){
            queue?.cancelAll(NETWORK_TAG)
        }

        fun getGson(): Gson {
            return gson
        }
    }
}