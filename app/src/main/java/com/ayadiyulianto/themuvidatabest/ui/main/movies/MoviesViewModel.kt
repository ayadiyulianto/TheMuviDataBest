package com.ayadiyulianto.themuvidatabest.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {

    fun getDiscoverMovies(): LiveData<Resource<PagedList<MovieEntity>>> =
        tmdbRepository.getDiscoverMovies()
}