package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.Movie
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.core.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovieDetail = DataDummy.generateDummyMovieDetail()[0]
    private val movieId = dummyMovieDetail.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<Resource<Movie>>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(tmdbUseCase)
    }

    @Test
    fun getMovieDetail() = runTest{
        val expected = flowOf(Resource.Success(dummyMovieDetail))
        `when`(tmdbUseCase.getMovieDetail(movieId.toString())).thenReturn(expected)

        viewModel.getMovieDetail(movieId).observeForever( observer)
        verify(observer).onChanged(expected.first())

        val expectedValue = expected.first().data
        val actualValue = viewModel.getMovieDetail(movieId).getOrAwaitValue().data

        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun addFavoriteMovie() = runTest{
        val expected = flowOf(Resource.Success(dummyMovieDetail))
        `when`(tmdbUseCase.getMovieDetail(movieId.toString())).thenReturn(expected)

        viewModel.setFavorite()
        viewModel.getMovieDetail(movieId).observeForever(observer)
        verify(observer).onChanged(expected.first())

        val expectedValue = expected.first().data
        val actualValue = viewModel.getMovieDetail(movieId).getOrAwaitValue().data

        assertEquals(expectedValue, actualValue)
    }
}