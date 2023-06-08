package com.ayadiyulianto.themuvidatabest.core.data.source.local.room

import androidx.room.*
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowWithSeasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TmdbDao {

    //Movie
    @Query("SELECT * FROM movies ORDER BY voteCount DESC")
    fun getDiscoverMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies where favorited = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun getMovieById(movieId: String): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    //Tv Show
    @Query("SELECT * FROM tvShows ORDER BY voteCount DESC")
    fun getDiscoverTvShow(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvShows where favorited = 1")
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvShows WHERE tvShowId = :tvShowId")
    fun getTvShowById(tvShowId: String): Flow<TvShowEntity>

    @Query("SELECT * FROM seasons LEFT JOIN tvShows ON tvShows.tvShowId = seasons.tvShowId WHERE seasons.tvShowId = :tvShowId")
    fun getSeasonByTvShowId(tvShowId: String): Flow<TvShowWithSeasonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeason(season: List<SeasonEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}