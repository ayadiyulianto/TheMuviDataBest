package com.ayadiyulianto.themuvidatabest.ui.main.favorites.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(private val tmdbRepository: TmdbRepository): ViewModel(){
    fun getMovieFav(): LiveData<PagedList<MovieEntity>> {
        return tmdbRepository.getFavoriteMovie()
    }
}