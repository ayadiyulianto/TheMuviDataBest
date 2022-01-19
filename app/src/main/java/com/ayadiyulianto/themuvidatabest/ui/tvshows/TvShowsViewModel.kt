package com.ayadiyulianto.themuvidatabest.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository

class TvShowsViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {
    fun getDiscoverTvShow(): LiveData<List<TvShowEntity>> = tmdbRepository.getDiscoverTvShow()
    fun getLoading():LiveData<Boolean> = tmdbRepository.isLoading
}