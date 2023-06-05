package com.ayadiyulianto.themuvidatabest.core.data.source.local

import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowWithSeasonEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.room.TmdbDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val tmdbDao: TmdbDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(tmdbDao: TmdbDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(tmdbDao)
    }

    fun getAllMovies(): Flow<List<MovieEntity>> = tmdbDao.getDiscoverMovie()

    fun getMovieById(movieId: String): Flow<MovieEntity> =
        tmdbDao.getMovieById(movieId)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = tmdbDao.getFavoriteMovie()

    fun getAllTvShow(): Flow<List<TvShowEntity>> = tmdbDao.getDiscoverTvShow()

    fun getTvShowById(showId: String): Flow<TvShowEntity> =
        tmdbDao.getTvShowById(showId)

    fun getFavoriteTvShow(): Flow<List<TvShowEntity>> = tmdbDao.getFavoriteTvShow()

    fun getTvShowWithSeason(showId: String): Flow<TvShowWithSeasonEntity> =
        tmdbDao.getSeasonByTvShowId(showId)

    fun insertMovies(movies: List<MovieEntity>) = tmdbDao.insertMovie(movies)

    fun insertTvShow(tvShows: List<TvShowEntity>) = tmdbDao.insertTvShow(tvShows)

    fun insertSeason(seasons: List<SeasonEntity>) = tmdbDao.insertSeason(seasons)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        tmdbDao.updateMovie(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorited = newState
        tmdbDao.updateTvShow(tvShow)
    }

    fun updateMovie(movie: MovieEntity) {
        tmdbDao.updateMovie(movie)
    }

    fun updateTvShow(tvShow: TvShowEntity) {
        tmdbDao.updateTvShow(tvShow)
    }
}