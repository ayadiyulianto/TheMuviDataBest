package com.ayadiyulianto.themuvidatabest.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.core.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.util.getOrAwaitValue
import kotlinx.coroutines.flow.flowOf
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
    private val movies = DataDummy.generateDummyMovie()

    @Mock
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<Movie>>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(tmdbUseCase)
    }

    @Test
    fun getMovies() {
        val dummyMovie= Resource.Success(movies)
        val movie = flowOf(dummyMovie)
        `when`(tmdbUseCase.getDiscoverMovies()).thenReturn(movie)

        val movieEntities = viewModel.getDiscoverMovies().getOrAwaitValue().data
        verify(tmdbUseCase).getDiscoverMovies()
        assertNotNull(movieEntities)
        assertEquals(2, movieEntities?.size)

        viewModel.getDiscoverMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}