package com.ayadiyulianto.themuvidatabest.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {

    fun getDiscoverTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> =
        tmdbRepository.getDiscoverTvShow()
}