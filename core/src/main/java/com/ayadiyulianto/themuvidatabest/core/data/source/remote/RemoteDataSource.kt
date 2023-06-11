package com.ayadiyulianto.themuvidatabest.core.data.source.remote

import android.util.Log
import com.ayadiyulianto.themuvidatabest.core.BuildConfig
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.network.ApiResponse
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.network.ApiService
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.*
import com.ayadiyulianto.themuvidatabest.core.util.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getDiscoverMovie(): Flow<ApiResponse<List<ResultsItemMovie>>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response = apiService.getDiscoverMovie(API_KEY, language)
                val results = response.results
                if (results.isNotEmpty()) {
                    emit(ApiResponse.Success(results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
        EspressoIdlingResource.decrement()
        return f
    }

    suspend fun getDiscoverTvShow(): Flow<ApiResponse<List<ResultsItemTvShow>>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response = apiService.getDiscoverTvShow(API_KEY, language)
                val results = response.results
                if (results.isNotEmpty()) {
                    emit(ApiResponse.Success(results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
        EspressoIdlingResource.decrement()
        return f
    }

    suspend fun getMovie(movieId: String): Flow<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response =
                    apiService.getMovieDetail(movieId, API_KEY, language, "videos")
                emit(ApiResponse.Success(response))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }
        EspressoIdlingResource.decrement()
        return f
    }

    suspend fun getTvShow(showId: String): Flow<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response =
                    apiService.getTvShowDetail(showId, API_KEY, language, "videos")
                emit(ApiResponse.Success(response))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }
        EspressoIdlingResource.decrement()
        return f
    }

    suspend fun getSearchResult(title: String) : Flow<ApiResponse<SearchResponse>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response =
                    apiService.getSearchResult(API_KEY, language, title, "1")
                emit(ApiResponse.Success(response))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }
        EspressoIdlingResource.decrement()
        return f
    }

    suspend fun getTrendings() : Flow<ApiResponse<SearchResponse>> {
        EspressoIdlingResource.increment()
        val f = flow {
            try {
                val response = apiService.getTrendings(API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }
        EspressoIdlingResource.decrement()
        return f
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        private const val API_KEY = BuildConfig.TMDB_API_KEY
        private const val language = "en-US"

    }
}