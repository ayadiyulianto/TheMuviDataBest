package com.ayadiyulianto.themuvidatabest.data.source.remote.api

import com.ayadiyulianto.themuvidatabest.data.source.remote.response.DiscoverMovieResponse
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.DiscoverTvShowResponse
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.MovieDetailResponse
import com.ayadiyulianto.themuvidatabest.data.source.remote.response.TvShowDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<DiscoverMovieResponse>

    @GET("discover/tv")
    fun getDiscoverTvShow(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<DiscoverTvShowResponse>

    @GET("movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String? = null
    ): Call<MovieDetailResponse>

    @GET("tv/{tvShowId}")
    fun getDetailTvShow(
        @Path("tvShowId") tvShowId: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String? = null
    ): Call<TvShowDetailResponse>
}