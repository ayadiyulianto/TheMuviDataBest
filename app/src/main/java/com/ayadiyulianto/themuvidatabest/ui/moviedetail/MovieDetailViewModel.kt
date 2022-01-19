package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.MovieDetailEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository

class MovieDetailViewModel(private val tmdbRepository: TmdbRepository): ViewModel(){
    fun getMovie(movieId: String): LiveData<MovieDetailEntity> = tmdbRepository.getMovieDetail(movieId)
    fun getLoading(): LiveData<Boolean> = tmdbRepository.isLoading
}