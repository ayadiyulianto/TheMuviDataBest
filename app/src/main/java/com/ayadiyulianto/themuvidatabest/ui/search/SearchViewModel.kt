package com.ayadiyulianto.themuvidatabest.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.SearchItem
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class SearchViewModel //@Inject constructor
    (private val tmdbUseCase: TmdbUseCase) :
    ViewModel() {

    fun getTrendings(): LiveData<Resource<List<SearchItem>>> = tmdbUseCase.getTrendings().asLiveData()

    fun getSearchResult(title: String): LiveData<Resource<List<SearchItem>>> =
        tmdbUseCase.getSearchResult(title).asLiveData()
}