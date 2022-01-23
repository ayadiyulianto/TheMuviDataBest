package com.ayadiyulianto.themuvidatabest.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.LiveDataTestUtil
import com.ayadiyulianto.themuvidatabest.vo.Resource
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
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

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(tmdbRepository)
    }

    @Test
    fun getMovieDetail() {
        val movieDummy = MutableLiveData<Resource<MovieEntity>>()
        val resource = Resource.success(dummyMovieDetail)
        movieDummy.value = resource
        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(movieDummy)

        val observer = mock(Observer::class.java) as Observer<Resource<MovieEntity>>
        viewModel.getMovieDetail(movieId.toString()).observeForever(observer)
        verify(observer).onChanged(resource)
    }

    @Test
    fun addFavoriteMovie(){
        val movieDummy = MutableLiveData<Resource<MovieEntity>>()
        val resource = Resource.success(dummyMovieDetail)
        movieDummy.value = resource
        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(movieDummy)

        val statusFavorite = true
        tmdbRepository.setFavoriteMovie(dummyMovieDetail, statusFavorite)

        val expectedResult = dummyMovieDetail
        expectedResult.favorited = statusFavorite
        viewModel.setFavorite()

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(dummyMovieDetail.movieId.toString()))
        MatcherAssert.assertThat(byId.data, IsEqual.equalTo(expectedResult))
    }

    @Test
    fun removeFavoriteMovie(){
        val movieDummy = MutableLiveData<Resource<MovieEntity>>()
        val resource = Resource.success(dummyMovieDetail)
        movieDummy.value = resource
        `when`(tmdbRepository.getMovieDetail(movieId.toString())).thenReturn(movieDummy)

        val statusFavorite = false
        tmdbRepository.setFavoriteMovie(dummyMovieDetail, statusFavorite)

        val expectedResult = dummyMovieDetail
        expectedResult.favorited = statusFavorite
        viewModel.setFavorite()

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(dummyMovieDetail.movieId.toString()))
        MatcherAssert.assertThat(byId.data, IsEqual.equalTo(expectedResult))
    }
}