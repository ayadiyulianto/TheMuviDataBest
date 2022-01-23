package com.ayadiyulianto.themuvidatabest.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(tmdbRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie= DataDummy.generateDummyMovie()
        val movie = MutableLiveData<List<MovieEntity>>()
        movie.value = DataDummy.generateDummyMovie()

        `when`(tmdbRepository.getDiscoverMovies()).thenReturn(movie)

        val dataListMovie = viewModel.getDiscoverMovies().value
        verify(tmdbRepository).getDiscoverMovies()
        assertNotNull(dataListMovie)
        assertEquals(10, dataListMovie?.size)

        viewModel.getDiscoverMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}