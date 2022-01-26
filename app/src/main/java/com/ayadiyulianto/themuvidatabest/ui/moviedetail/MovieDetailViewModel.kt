package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {

    var movieData: LiveData<Resource<MovieEntity>> = MutableLiveData()

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        movieData = tmdbRepository.getMovieDetail(movieId.toString())
        return movieData
    }

    fun setFavorite(): Boolean {
        val movieResource = movieData.value
        if (movieResource != null) {
            val movieDetail = movieResource.data
            val newState = !(movieDetail?.favorited ?: false)
            if (movieDetail != null) {
                tmdbRepository.setFavoriteMovie(movieDetail, newState)
                return newState
            }
        }
        return false
    }
}