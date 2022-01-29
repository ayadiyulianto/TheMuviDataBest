package com.ayadiyulianto.themuvidatabest.ui.main.favorites.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
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
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = FavoriteMoviesViewModel(tmdbRepository)
    }

    @Test
    fun getMovieFav() {
        Mockito.`when`(pagedList.size).thenReturn(1)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = pagedList
        Mockito.`when`(tmdbRepository.getFavoriteMovie()).thenReturn(movies)

        val showEntities = viewModel.getMovieFav().value
        Mockito.verify(tmdbRepository).getFavoriteMovie()
        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.getMovieFav().observeForever(observer)
        Mockito.verify(observer).onChanged(pagedList)
    }
}