package com.ayadiyulianto.themuvidatabest.ui.main.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.ui.movies.MoviesViewModel
import com.ayadiyulianto.themuvidatabest.vo.Resource
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
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(tmdbRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie= Resource.success(pagedList)
        `when`(dummyMovie.data?.size).thenReturn(1)
        val movie = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movie.value = dummyMovie
        `when`(tmdbRepository.getDiscoverMovies()).thenReturn(movie)

        val movieEntities = viewModel.getDiscoverMovies().value?.data
        verify(tmdbRepository).getDiscoverMovies()
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        viewModel.getDiscoverMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}