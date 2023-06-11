package com.ayadiyulianto.themuvidatabest.favorite.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.favorite.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.favorite.util.getOrAwaitValue
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMoviesViewModelTest {

    private lateinit var viewModel: FavoriteMoviesViewModel

    @Mock
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<List<Movie>>

    @Mock
    private lateinit var pagedList: List<Movie>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = FavoriteMoviesViewModel(tmdbUseCase)
    }

    @Test
    fun getMovieFav() {
        Mockito.`when`(pagedList.size).thenReturn(1)
        val movies = flowOf(pagedList)
        Mockito.`when`(tmdbUseCase.getFavoriteMovie()).thenReturn(movies)

        val showEntities = viewModel.getMovieFav().getOrAwaitValue()
        Mockito.verify(tmdbUseCase).getFavoriteMovie()
        assertNotNull(showEntities)
        assertEquals(1, showEntities.size)

        viewModel.getMovieFav().observeForever(observer)
        Mockito.verify(observer).onChanged(pagedList)
    }
}