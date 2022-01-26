package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovieDetail = DataDummy.generateDummyMovieDetail()[0]
    private val movieId = dummyMovieDetail.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(tmdbRepository)
    }

    @Test
    fun getMovieDetail() {
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovieDetail)
        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(expected)

        viewModel.getMovieDetail(movieId).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieData.value

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun addFavoriteMovie(){
        val expected = MutableLiveData<Resource<MovieEntity>>()
        expected.value = Resource.success(dummyMovieDetail)
        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(expected)

        viewModel.setFavorite()
        viewModel.getMovieDetail(movieId).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.movieData.value

        assertEquals(expectedValue, actualValue)
    }
}