package com.ayadiyulianto.themuvidatabest.favorite.ui.main.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class FavoriteTvShowsViewModel //@Inject constructor
    (private val tmdbUseCase: TmdbUseCase): ViewModel() {
    fun getTvShowFav(): LiveData<List<TvShow>> =
        tmdbUseCase.getFavoriteTvShow().asLiveData()
}