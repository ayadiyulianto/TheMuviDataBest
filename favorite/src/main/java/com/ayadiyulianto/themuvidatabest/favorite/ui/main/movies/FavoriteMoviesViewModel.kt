package com.ayadiyulianto.themuvidatabest.favorite.ui.main.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase

//@HiltViewModel
class FavoriteMoviesViewModel //@Inject constructor
    (private val tmdbUseCase: TmdbUseCase): ViewModel(){
    fun getMovieFav(): LiveData<List<Movie>> =
        tmdbUseCase.getFavoriteMovie().asLiveData()
}