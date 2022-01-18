package com.ayadiyulianto.themuvidatabest.movies

import com.ayadiyulianto.themuvidatabest.ui.movies.MoviesViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel
    @Before
    fun setUp() {
        viewModel = MoviesViewModel()
    }

    @Test
    fun getMovies() {
        val movieEntities = viewModel.getMovies()
        assertNotNull(movieEntities)
        assertEquals(12, movieEntities.size)
    }
}