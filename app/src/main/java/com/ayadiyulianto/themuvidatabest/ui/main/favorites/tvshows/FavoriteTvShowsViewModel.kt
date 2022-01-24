package com.ayadiyulianto.themuvidatabest.ui.main.favorites.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowsViewModel @Inject constructor(private val tmdbRepository: TmdbRepository): ViewModel() {
    fun getTvShowFav(): LiveData<PagedList<TvShowEntity>> {
        return tmdbRepository.getFavoriteTvShow()
    }
}