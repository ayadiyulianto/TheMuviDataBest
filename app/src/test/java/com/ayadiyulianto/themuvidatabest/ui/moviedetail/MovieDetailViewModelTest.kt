package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.MovieDetailEntity
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
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovieDetail = DataDummy.generateDummyMovieDetail()[0]
    private val movieId = dummyMovieDetail.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<MovieDetailEntity>

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(tmdbRepository)
        viewModel.getMovieDetail(movieId.toString())
    }

    @Test
    fun getMovieDetail() {
        val movieDummy = MutableLiveData<MovieDetailEntity>()
        movieDummy.value = dummyMovieDetail

        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(movieDummy)

        verify(tmdbRepository).getMovieDetail(movieId.toString())

        val movieData = viewModel.getMovieDetail(movieId.toString()).value as MovieDetailEntity

        assertNotNull(movieData)

        assertEquals(dummyMovieDetail.posterPath, movieData.posterPath)
        assertEquals(dummyMovieDetail.backdropPath, movieData.backdropPath)
        assertEquals(dummyMovieDetail.id, movieData.id)
        assertEquals(dummyMovieDetail.originalTitle, movieData.originalTitle)
        assertEquals(dummyMovieDetail.title, movieData.title)
        assertEquals(dummyMovieDetail.overview, movieData.overview)
        assertEquals(dummyMovieDetail.voteAverage, movieData.voteAverage)
        assertEquals(dummyMovieDetail.voteCount, movieData.voteCount)
        assertEquals(dummyMovieDetail.genres, movieData.genres)
        assertEquals(dummyMovieDetail.runtime, movieData.runtime)
        assertEquals(dummyMovieDetail.releaseDate, movieData.releaseDate)

        viewModel.getMovieDetail(movieId.toString()).observeForever(observer)
        verify(observer).onChanged(dummyMovieDetail)
    }
}