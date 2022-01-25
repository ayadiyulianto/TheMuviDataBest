package com.ayadiyulianto.themuvidatabest.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ayadiyulianto.themuvidatabest.BuildConfig
import com.ayadiyulianto.themuvidatabest.data.source.remote.api.ApiConfig
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.*
import com.ayadiyulianto.themuvidatabest.util.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        private const val API_KEY = BuildConfig.TMDB_API_KEY
        private const val language = "en-US"

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getDiscoverMovie(): LiveData<ApiResponse<List<ResultsItemMovie>>> {
        EspressoIdlingResource.increment()
        val resultMovieResponse = MutableLiveData<ApiResponse<List<ResultsItemMovie>>>()
        CoroutineScope(IO).launch {
            try {
                val response = ApiConfig.getApiService().getDiscoverMovie(API_KEY, language).await()
                resultMovieResponse.postValue(ApiResponse.success(response.results))
            } catch (e: IOException) {
                Log.e("getDiscoverMovie Error", e.message.toString())
                resultMovieResponse.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultMovieResponse
    }

    fun getDiscoverTvShow(): LiveData<ApiResponse<List<ResultsItemTvShow>>> {
        EspressoIdlingResource.increment()
        val resultTvShowResponse = MutableLiveData<ApiResponse<List<ResultsItemTvShow>>>()
        CoroutineScope(IO).launch {
            try {
                val response =
                    ApiConfig.getApiService().getDiscoverTvShow(API_KEY, language).await()
                resultTvShowResponse.postValue(ApiResponse.success(response.results))
            } catch (e: IOException) {
                Log.e("getDiscoverTvShow Error", e.message.toString())
                resultTvShowResponse.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        mutableListOf()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultTvShowResponse
    }

    fun getMovie(movieId: String): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultsItemMovie = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        CoroutineScope(IO).launch {
            try {
                val response =
                    ApiConfig.getApiService().getMovieDetail(movieId, API_KEY, language, "videos")
                        .await()
                resultsItemMovie.postValue(ApiResponse.success(response))
            } catch (e: IOException) {
                Log.e("getMovie Error", e.message.toString())
                resultsItemMovie.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        MovieDetailResponse()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultsItemMovie
    }

    fun getTvShow(showId: String): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultsItemTvShow = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        CoroutineScope(IO).launch {
            try {
                val response =
                    ApiConfig.getApiService().getTvShowDetail(showId, API_KEY, language, "videos")
                        .await()
                resultsItemTvShow.postValue(ApiResponse.success(response))
            } catch (e: IOException) {
                Log.e("getTvShow Error", e.message.toString())
                resultsItemTvShow.postValue(
                    ApiResponse.error(
                        e.message.toString(),
                        TvShowDetailResponse()
                    )
                )
            }
        }
        EspressoIdlingResource.decrement()
        return resultsItemTvShow
    }

    suspend fun getSearchResult(title: String, callback: CallbackLoadSearchResult) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getSearchResult(API_KEY, language, title, "1")
            .await().results.let { listResult ->
                callback.onSearchResultRecieved(
                    (
                            listResult
                            )
                )
                EspressoIdlingResource.decrement()
            }
    }

    suspend fun getTrendings(callback: CallbackLoadTrendings) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getTrendings(API_KEY)
            .await().results.let { listResult ->
                callback.onTrendingsRecieved(
                    (
                            listResult
                            )
                )
                EspressoIdlingResource.decrement()
            }
    }

    interface CallbackLoadSearchResult {
        fun onSearchResultRecieved(showResponse: List<SearchResultsItem?>?)
    }

    interface CallbackLoadTrendings {
        fun onTrendingsRecieved(showResponse: List<SearchResultsItem?>?)
    }
}