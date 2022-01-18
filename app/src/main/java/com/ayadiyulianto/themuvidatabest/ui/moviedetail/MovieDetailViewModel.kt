package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.MovieEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy

class MovieDetailViewModel: ViewModel(){

    private var movieId: Long = 0L

    fun setSelectedMovie(movieId: Long) {
        this.movieId = movieId
    }

    fun getMovie(): MovieEntity {
        lateinit var movie: MovieEntity
        val moviesEntities = DataDummy.generateDummyMovie()
        for (movieEntity in moviesEntities) {
            if (movieEntity.id == movieId) {
                movie = movieEntity
            }
        }
        return movie
    }

}