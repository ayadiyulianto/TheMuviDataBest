package com.ayadiyulianto.themuvidatabest.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository

class MoviesViewModel(private val tmdbRepository: TmdbRepository) : ViewModel() {
    fun getDiscoverMovies(): LiveData<List<MovieEntity>> = tmdbRepository.getDiscoverMovies()
    fun getLoading(): LiveData<Boolean> = tmdbRepository.isLoading
}