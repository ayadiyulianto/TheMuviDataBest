package com.ayadiyulianto.themuvidatabest.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.data.source.local.room.TmdbDao

class LocalDataSource private constructor(private val mTmdbDao: TmdbDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(tmdbDao: TmdbDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(tmdbDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mTmdbDao.getDiscoverMovie()

    fun getMovieById(movieId: String): LiveData<MovieEntity> =
        mTmdbDao.getMovieById(movieId)

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity> = mTmdbDao.getFavoriteMovie()

    fun getAllTvShow(): DataSource.Factory<Int, TvShowEntity> = mTmdbDao.getDiscoverTvShow()

    fun getTvShowById(showId: String): LiveData<TvShowEntity> =
        mTmdbDao.getTvShowById(showId)

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> = mTmdbDao.getFavoriteTvShow()

    fun getTvShowWithSeason(showId: String): LiveData<TvShowWithSeason> =
        mTmdbDao.getSeasonByTvShowId(showId)

    fun insertMovies(movies: ArrayList<MovieEntity>) = mTmdbDao.insertMovie(movies)

    fun insertTvShow(tvShows: ArrayList<TvShowEntity>) = mTmdbDao.insertTvShow(tvShows)

    fun insertSeason(seasons: List<SeasonEntity>) = mTmdbDao.insertSeason(seasons)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mTmdbDao.updateMovie(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorited = newState
        mTmdbDao.updateTvShow(tvShow)
    }

    fun updateMovie(movie: MovieEntity) {
        mTmdbDao.updateMovie(movie)
    }

    fun updateTvShow(tvShow: TvShowEntity) {
        mTmdbDao.updateTvShow(tvShow)
    }
}