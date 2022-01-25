package com.ayadiyulianto.themuvidatabest.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.data.source.remote.entity.SearchEntity
import com.ayadiyulianto.themuvidatabest.vo.Resource

interface TmdbDataSource {
    fun getDiscoverMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getDiscoverTvShow(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getMovieDetail(movieId: String): LiveData<Resource<MovieEntity>>
    fun getTvShowDetail(showId: String): LiveData<Resource<TvShowEntity>>
    fun getTvShowWithSeason(showId: String): LiveData<TvShowWithSeason>
    fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>>
    fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>>
    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean)
    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean)
    fun getSearchResult(title: String): LiveData<List<SearchEntity>>
    fun getTrendings(): LiveData<List<SearchEntity>>
}