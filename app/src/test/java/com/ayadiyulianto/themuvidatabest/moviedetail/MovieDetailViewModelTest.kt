package com.ayadiyulianto.themuvidatabest.moviedetail

import com.ayadiyulianto.themuvidatabest.ui.moviedetail.MovieDetailViewModel
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val movieId = dummyMovie.id

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel()
    }

    @Test
    fun getMovie() {
        viewModel.setSelectedMovie(movieId)
        val movieEntity = viewModel.getMovie()

        assertNotNull(movieEntity)

        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.description, movieEntity.description)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.duration, movieEntity.duration)
        assertEquals(dummyMovie.rating.toString(), movieEntity.rating.toString())
        assertEquals(dummyMovie.backdropURL, movieEntity.backdropURL)
        assertEquals(dummyMovie.posterURL, movieEntity.posterURL)
        assertEquals(dummyMovie.youtubeVideoId, movieEntity.youtubeVideoId)
    }
}