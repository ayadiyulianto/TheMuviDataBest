package com.ayadiyulianto.themuvidatabest.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason

@Dao
interface TmdbDao {

    //Movie
    @Query("SELECT * FROM movies ORDER BY movieId ASC")
    fun getDiscoverMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies where favorited = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    //Tv Show
    @Query("SELECT * FROM tvShows ORDER BY tvShowId ASC")
    fun getDiscoverTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvShows where favorited = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvShows WHERE tvShowId = :tvShowId")
    fun getTvShowById(tvShowId: String): LiveData<TvShowEntity>

    @Query("SELECT * FROM seasons INNER JOIN tvShows ON tvShows.tvShowId = seasons.tvShowId WHERE seasons.tvShowId = :tvShowId")
    fun getSeasonByTvShowId(tvShowId: String): LiveData<TvShowWithSeason>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeason(season: List<SeasonEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}