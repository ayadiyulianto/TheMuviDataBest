package com.ayadiyulianto.themuvidatabest.data.source.remote.api

import com.ayadiyulianto.themuvidatabest.data.source.remote.response.*
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
    fun getMovieDetail(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String? = null
    ): Call<MovieDetailResponse>

    @GET("tv/{tvShowId}")
    fun getTvShowDetail(
        @Path("tvShowId") tvShowId: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String? = null
    ): Call<TvShowDetailResponse>

    @GET("search/multi")
    fun getSearchResult(
        @Query("api_key") api_key: String?,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Call<SearchResponse>

    @GET("trending/all/week")
    fun getTrendings(
        @Query("api_key") api_key: String?
    ): Call<SearchResponse>
}