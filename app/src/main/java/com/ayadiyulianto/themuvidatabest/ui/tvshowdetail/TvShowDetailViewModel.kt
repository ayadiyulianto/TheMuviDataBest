package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TvShowDetailEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository

class TvShowDetailViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {
    fun getTvShowDetail(showId: String): LiveData<TvShowDetailEntity> = tmdbRepository.getTvShowDetail(showId)
    fun isLoading():LiveData<Boolean> = tmdbRepository.isLoading
}