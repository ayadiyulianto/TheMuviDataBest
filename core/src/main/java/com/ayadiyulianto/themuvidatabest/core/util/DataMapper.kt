package com.ayadiyulianto.themuvidatabest.core.util

import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.SeasonEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.TvShowWithSeasonEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.MovieDetailResponse
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.ResultsItemMovie
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.ResultsItemTvShow
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.SearchResultsItem
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.SeasonsItem
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.TvShowDetailResponse
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.response.VideoResults
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.model.SearchItem
import com.ayadiyulianto.themuvidatabest.core.domain.model.Season
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShowWithSeason
import org.json.JSONArray

object DataMapper {
    private const val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

    private fun String.toImageUrl() : String {
        return "$imageBaseUrl$this"
    }

    fun ResultsItemMovie.toEntity(): MovieEntity {
        return MovieEntity(
            movieId = id,
            title = title,
            overview = overview,
            posterPath = posterPath.toImageUrl(),
            backdropPath = backdropPath.toImageUrl(),
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    fun MovieEntity.toDomain() : Movie {
        return Movie(
            movieId = movieId,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            voteCount = voteCount,
            voteAverage = voteAverage,
            runtime = runtime,
            genres = genres,
            youtubeTrailerId = youtubeTrailerId,
            favorited = favorited
        )
    }

    fun Movie.toEntity() : MovieEntity {
        return MovieEntity(
            movieId = movieId,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            voteCount = voteCount,
            voteAverage = voteAverage,
            runtime = runtime,
            genres = genres,
            youtubeTrailerId = youtubeTrailerId,
            favorited = favorited
        )
    }

    fun MovieDetailResponse.toEntity() : MovieEntity {
        val listOfGenre = ArrayList<String>()
        if (genres != null) {
            for (genre in (genres)) {
                if (genre != null) {
                    genre.name?.let { listOfGenre.add(it) }
                }
            }
        }

        return MovieEntity(
            movieId = id,
            title = title ?: "",
            overview = overview ?: "",
            posterPath = posterPath?.toImageUrl() ?: "",
            backdropPath = backdropPath?.toImageUrl() ?: "",
            releaseDate = releaseDate ?: "",
            voteAverage = voteAverage ?: 0.0,
            voteCount = voteCount ?: 0,
            runtime = runtime ?: 0,
            genres = JSONArray(listOfGenre).toString(),
            youtubeTrailerId = videos?.getYoutubeTrailerId() ?: ""
        )
    }

    fun TvShowEntity.toDomain() : TvShow {
        return TvShow(
            tvShowId = tvShowId,
            name= name,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            voteCount = voteCount,
            voteAverage = voteAverage,
            firstAirDate = firstAirDate,
            genres = genres,
            runtime = runtime,
            youtubeTrailerId = youtubeTrailerId,
            favorited = favorited
        )
    }

    fun ResultsItemTvShow.toEntity() : TvShowEntity {
        return TvShowEntity(
            tvShowId = id,
            name= name,
            overview = overview,
            posterPath = posterPath.toImageUrl(),
            backdropPath = backdropPath.toImageUrl(),
            voteCount = voteCount,
            voteAverage = voteAverage,
            firstAirDate = firstAirDate
        )
    }

    fun TvShowDetailResponse.toEntity() : TvShowEntity {
        val listOfGenre = ArrayList<String>()
        if (genres != null) {
            for (genre in (genres)) {
                if (genre != null) {
                    genre.name?.let { listOfGenre.add(it) }
                }
            }
        }

        return TvShowEntity(
            tvShowId = id,
            name= name ?: "",
            overview = overview ?: "",
            posterPath = posterPath?.toImageUrl() ?: "",
            backdropPath = backdropPath?.toImageUrl() ?: "",
            voteCount = voteCount ?: 0,
            voteAverage = voteAverage ?: 0.0,
            firstAirDate = firstAirDate ?: "",
            genres = JSONArray(listOfGenre).toString(),
            runtime = (if (!episodeRunTime.isNullOrEmpty()) episodeRunTime[0] else 0) ?: 0,
            youtubeTrailerId = videos?.getYoutubeTrailerId() ?: ""
        )
    }

    fun TvShow.toEntity() : TvShowEntity {
        return TvShowEntity(
            tvShowId = tvShowId,
            name= name,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            voteCount = voteCount,
            voteAverage = voteAverage,
            firstAirDate = firstAirDate,
            genres = genres,
            runtime = runtime,
            youtubeTrailerId = youtubeTrailerId,
            favorited = favorited
        )
    }

    fun TvShowDetailResponse.getSeasonEntity() : List<SeasonEntity>? {
        return seasons?.map {
            it.toEntity(id)
        }
    }

    fun SeasonsItem.toEntity(tvShowId: Int) : SeasonEntity {
        return SeasonEntity(
            seasonId = id,
            tvShowId = tvShowId,
            name = name ?: "",
            overview = overview ?: "",
            airDate = airDate ?: "",
            seasonNumber = seasonNumber ?: 0,
            episodeCount = episodeCount ?: 0,
            posterPath = posterPath?.toImageUrl() ?: ""
        )
    }

    fun SeasonEntity.toDomain() : Season {
        return Season(
            seasonId = seasonId,
            tvShowId = tvShowId,
            name = name,
            overview = overview,
            airDate = airDate,
            seasonNumber = seasonNumber,
            episodeCount = episodeCount,
            posterPath = posterPath
        )
    }


    fun TvShowWithSeasonEntity.toDomain() : TvShowWithSeason {
        return TvShowWithSeason(
            tvShow = tvShow.toDomain(),
            seasons = seasons.map {
                it.toDomain()
            }
        )
    }

    fun SearchResultsItem.toDomain() : SearchItem {
        return SearchItem(
            id = id,
            name = (if (mediaType == "tv") name else title)  ?: "",
            posterPath = posterPath?.toImageUrl() ?: "",
            backdropPath = backdropPath?.toImageUrl() ?: "",
            mediaType = mediaType,
            overview = overview ?: "",
            voteCount = voteCount ?: 0,
            voteAverage = voteAverage ?: 0.0,
            releaseOrAirDate = (if (mediaType == "tv") firstAirDate else releaseDate)  ?: ""
        )
    }

    fun SearchResultsItem.toTvShowEntity() : TvShowEntity {
        return TvShowEntity(
            tvShowId = id,
            name = name ?: "",
            posterPath = posterPath?.toImageUrl() ?: "",
            backdropPath = backdropPath?.toImageUrl() ?: "",
            overview = overview ?: "",
            voteCount = voteCount ?: 0,
            voteAverage = voteAverage ?: 0.0,
            firstAirDate = firstAirDate ?: ""
        )
    }

    fun SearchResultsItem.toMovieEntity() : MovieEntity {
        return MovieEntity(
            movieId = id,
            title = title ?: "",
            posterPath = posterPath?.toImageUrl() ?: "",
            backdropPath = backdropPath?.toImageUrl() ?: "",
            overview = overview ?: "",
            voteCount = voteCount ?: 0,
            voteAverage = voteAverage ?: 0.0,
            releaseDate = releaseDate ?: ""
        )
    }

    private fun VideoResults.getYoutubeTrailerId(): String? {
        val ytTrailer = this.results?.filter { it.site == "YouTube" && it.type == "Trailer" }
        if (!ytTrailer.isNullOrEmpty()) {
            return ytTrailer[0].key
        }
        val ytVideo = this.results?.filter { it.site == "YouTube" }
        if (!ytVideo.isNullOrEmpty()) {
            return ytVideo[0].key
        }
        return null
    }
}