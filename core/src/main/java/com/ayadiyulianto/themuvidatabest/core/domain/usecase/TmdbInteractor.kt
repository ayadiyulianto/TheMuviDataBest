package com.ayadiyulianto.themuvidatabest.core.domain.usecase

import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.model.SearchItem
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.core.domain.repository.ITmdbRepository
import kotlinx.coroutines.flow.Flow

class TmdbInteractor(private val tmdbRepository: ITmdbRepository) : TmdbUseCase {
    override fun getDiscoverMovies(): Flow<Resource<List<Movie>>> {
        return tmdbRepository.getDiscoverMovies()
    }

    override fun getDiscoverTvShow(): Flow<Resource<List<TvShow>>> {
        return tmdbRepository.getDiscoverTvShow()
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<Movie>> {
        return tmdbRepository.getMovieDetail(movieId)
    }

    override fun getTvShowDetail(showId: String): Flow<Resource<TvShow>> {
        return tmdbRepository.getTvShowDetail(showId)
    }

    override fun getTvShowWithSeason(showId: String): Flow<Resource<TvShowWithSeason>> {
        return tmdbRepository.getTvShowWithSeason(showId)
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return tmdbRepository.getFavoriteMovie()
    }

    override fun getFavoriteTvShow(): Flow<List<TvShow>> {
        return tmdbRepository.getFavoriteTvShow()
    }

    override fun setFavoriteMovie(movie: Movie, newState: Boolean) {
        return tmdbRepository.setFavoriteMovie(movie, newState)
    }

    override fun setFavoriteTvShow(tvShow: TvShow, newState: Boolean) {
        return tmdbRepository.setFavoriteTvShow(tvShow, newState)
    }

    override fun getSearchResult(title: String): Flow<Resource<List<SearchItem>>> {
        return tmdbRepository.getSearchResult(title)
    }

    override fun getTrendings(): Flow<Resource<List<SearchItem>>> {
        return tmdbRepository.getTrendings()
    }

}