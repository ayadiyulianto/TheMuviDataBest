package com.ayadiyulianto.themuvidatabest.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase

//@HiltViewModel
class TvShowsViewModel //@Inject constructor
    (private val tmdbUseCase: TmdbUseCase) :
    ViewModel() {

    fun getDiscoverTvShow(): LiveData<Resource<List<TvShow>>> =
        tmdbUseCase.getDiscoverTvShow().asLiveData()
}