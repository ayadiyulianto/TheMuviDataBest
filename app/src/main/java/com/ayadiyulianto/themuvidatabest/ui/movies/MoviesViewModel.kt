package com.ayadiyulianto.themuvidatabest.ui.movies

import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.MovieEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy

class MoviesViewModel : ViewModel() {

    fun getMovies(): List<MovieEntity> = DataDummy.generateDummyMovie()
}