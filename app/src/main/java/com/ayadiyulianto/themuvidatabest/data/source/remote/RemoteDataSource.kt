package com.ayadiyulianto.themuvidatabest.data.source.remote

import com.ayadiyulianto.themuvidatabest.BuildConfig
import com.ayadiyulianto.themuvidatabest.data.source.remote.api.ApiConfig
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.MovieDetailResponse
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.ResultsItemMovie
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.ResultsItemTvShow
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.TvShowDetailResponse
import com.ayadiyulianto.themuvidatabest.util.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource ? = null
        private const val API_KEY = BuildConfig.TMDB_API_KEY
        private const val language = "en-US"

        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource()
                }
    }

    suspend fun getDiscoverMovie(callback: CallbackLoadDiscoverMovie) {
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getDiscoverMovie(API_KEY, language).await().results.let{
                listMovie -> callback.onMoviesRecieved((
                    listMovie
                ))
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getDiscoverTvShow(callback: CallbackLoadDiscoverTvShow){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getDiscoverTvShow(API_KEY, language).await().results.let{
            listShow -> callback.onTvShowRecieved((
                listShow
                ))
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getMovie(movieId: String, callbackDetail: CallbackLoadMovieDetail){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getDetailMovie(movieId, API_KEY, language, "videos").await().let{
            movie -> callbackDetail.onMovieDetailsRecieved((
                movie
                ))
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTvShow(showId: String, callbackDetail: CallbackLoadTvShowDetail){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getDetailTvShow(showId, API_KEY, language, "videos").await().let{
            show -> callbackDetail.onTvShowDetailsRecieved((
                show
                ))
            EspressoIdlingResource.decrement()
        }
    }

    interface CallbackLoadDiscoverMovie{
        fun onMoviesRecieved(movieResponse: List<ResultsItemMovie>)
    }

    interface CallbackLoadDiscoverTvShow{
        fun onTvShowRecieved(showResponse: List<ResultsItemTvShow>)
    }

    interface CallbackLoadMovieDetail{
        fun onMovieDetailsRecieved(movieResponse: MovieDetailResponse)
    }

    interface CallbackLoadTvShowDetail{
        fun onTvShowDetailsRecieved(showResponse: TvShowDetailResponse)
    }

}