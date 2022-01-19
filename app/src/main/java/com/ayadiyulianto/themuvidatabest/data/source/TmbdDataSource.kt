package com.ayadiyulianto.themuvidatabest.data.source

import androidx.lifecycle.LiveData
import com.ayadiyulianto.themuvidatabest.data.MovieDetailEntity
import com.ayadiyulianto.themuvidatabest.data.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.TvShowDetailEntity
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity

interface TmdbDataSource {
    fun getDiscoverMovies(): LiveData<List<MovieEntity>>
    fun getDiscoverTvShow(): LiveData<List<TvShowEntity>>
    fun getMovieDetail(movieId: String): LiveData<MovieDetailEntity>
    fun getTvShowDetail(showId: String): LiveData<TvShowDetailEntity>
}