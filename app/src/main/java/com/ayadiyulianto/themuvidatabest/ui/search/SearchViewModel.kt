package com.ayadiyulianto.themuvidatabest.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.remote.entity.SearchEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {

    fun getTrendings(): LiveData<List<SearchEntity>> = tmdbRepository.getTrendings()

    fun getSearchResult(title: String): LiveData<List<SearchEntity>> =
        tmdbRepository.getSearchResult(title)

    fun getLoading(): LiveData<Boolean> = tmdbRepository.isLoading
}