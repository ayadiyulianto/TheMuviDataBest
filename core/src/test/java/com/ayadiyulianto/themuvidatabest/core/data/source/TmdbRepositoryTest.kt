package com.ayadiyulianto.themuvidatabest.core.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.core.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.core.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.core.util.AppExecutors
import com.ayadiyulianto.themuvidatabest.core.util.DataDummy
import com.ayadiyulianto.themuvidatabest.core.util.DataMapper.toEntity
import com.ayadiyulianto.themuvidatabest.core.util.MainDispatcherRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class TmdbRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var remote : RemoteDataSource

    @Mock
    private lateinit var local : LocalDataSource

    @Mock
    private lateinit var appExecutors : AppExecutors

    private lateinit var tmdbRepository : FakeTmdbRepository

    private val listMovie = DataDummy.generateDummyMovie()
    private val listTvShow = DataDummy.generateDummyTvShow()

    @Before
    fun setup() {
        tmdbRepository = FakeTmdbRepository(remote, local, appExecutors)
    }

//    @Test
//    fun getAllMovies() = runTest {
////        val movies = flowOf(listMovie.map { it.toEntity() })
//        val movies = flowOf(pagedList)
//        `when`(local.getAllMovies()).thenReturn(movies)
//
//        val movieEntity = tmdbRepository.getDiscoverMovies().asLiveData().getOrAwaitValue()
//        verify(local, atLeastOnce()).getAllMovies()
//        assertNotNull(movieEntity.data)
//        assertNotNull(movieEntity.data)
//        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
//    }
//
//    @Test
//    fun getAllTvShow() = runTest {
//        val l = flowOf(listTvShow.map { it.toEntity() })
//        `when`(local.getAllTvShow()).thenReturn(l)
//        tmdbRepository.getDiscoverTvShow()
//
//        val showEntity = Resource.Success(listTvShow)
//        verify(local).getAllTvShow()
//        assertNotNull(showEntity.data)
//        assertEquals(listTvShow.size.toLong(), showEntity.data?.size?.toLong())
//    }
//
//    @Test
//    fun getMovieDetails() = runTest {
//        val dummyMovie = flowOf(detailMovie.toEntity())
//        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)
//
//        val movieEntity = tmdbRepository.getMovieDetail(detailMovie.movieId.toString()).asLiveData()
//            .getOrAwaitValue()
//        verify(local).getMovieById(movieId.toString())
//        assertNotNull(movieEntity)
//        assertEquals(detailMovie.movieId, movieEntity.data?.movieId)
//    }
//
//    @Test
//    fun getTvShowDetail() = runTest {
//        val dummyTvShow = flowOf(detailTvShow.toEntity())
//        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)
//
//        val showEntity =
//            tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()).asLiveData()
//                .getOrAwaitValue()
//        verify(local).getTvShowById(tvShowId.toString())
//        assertNotNull(showEntity)
//        assertEquals(detailTvShow.tvShowId, showEntity.data?.tvShowId)
//    }
//
//    @Test
//    fun getTvShowDetailWithSeason() = runTest {
//        val dummyTvShowWithSeason = flowOf(detailTvShowWithSeason)
//        `when`(local.getTvShowWithSeason(tvShowId.toString())).thenReturn(dummyTvShowWithSeason.map { it.toEntity() })
//
//        val showEntity =
//            tmdbRepository.getTvShowWithSeason(detailTvShowWithSeason.tvShow.tvShowId.toString())
//                .asLiveData().getOrAwaitValue()
//        verify(local, atLeastOnce()).getTvShowWithSeason(tvShowId.toString())
//        assertNotNull(showEntity)
//        assertEquals(detailTvShow.tvShowId, showEntity.data?.tvShow?.tvShowId)
//    }

    @Test
    fun getListFavoriteMovie() = runTest {
        val dummyMovies = flowOf(listMovie.map { it.toEntity() })
        `when`(local.getFavoriteMovie()).thenReturn(dummyMovies)
        tmdbRepository.getFavoriteMovie()

        val movieEntity = Resource.Success(listMovie)
        verify(local).getFavoriteMovie()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getListFavoriteTvShow() = runTest {
        val dummyTvShow = flowOf(listTvShow.map { it.toEntity() })
        `when`(local.getFavoriteTvShow()).thenReturn(dummyTvShow)
        tmdbRepository.getFavoriteTvShow()

        val showEntity = Resource.Success(listTvShow)
        verify(local).getFavoriteTvShow()
        assertNotNull(showEntity.data)
        assertEquals(listTvShow.size.toLong(), showEntity.data?.size?.toLong())
    }
//
//    @Test
//    fun addFavoriteMovie() = runTest {
//        val dummyMovie = flowOf(detailMovie.toEntity())
//        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)
//
//        val statusFavorite = true
//        tmdbRepository.setFavoriteMovie(detailMovie, statusFavorite)
//
//        val expectedResult = detailMovie
//        expectedResult.favorited = statusFavorite
//
//        val byId =
//            tmdbRepository.getMovieDetail(detailMovie.movieId.toString()).asLiveData().getOrAwaitValue()
//        assertThat(byId.data, equalTo(expectedResult))
//    }
//
//    @Test
//    fun removeFavoriteMovie() = runTest {
//        val dummyMovie = flowOf(detailMovie.toEntity())
//        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)
//
//        val statusFavorite = false
//        tmdbRepository.setFavoriteMovie(detailMovie, statusFavorite)
//
//        val expectedResult = detailMovie
//        expectedResult.favorited = statusFavorite
//
//        val byId =
//            tmdbRepository.getMovieDetail(detailMovie.movieId.toString()).asLiveData().getOrAwaitValue()
//        assertThat(byId.data, equalTo(expectedResult))
//    }
//
//    @Test
//    fun addFavoriteTvShow() = runTest {
//        val dummyTvShow = flowOf(detailTvShow.toEntity())
//        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)
//
//        val statusFavorite = true
//        tmdbRepository.setFavoriteTvShow(detailTvShow, statusFavorite)
//
//        val expectedResult = detailTvShow
//        expectedResult.favorited = statusFavorite
//
//        val byId =
//            tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()).asLiveData().getOrAwaitValue()
//        assertThat(byId.data, equalTo(expectedResult))
//    }
//
//    @Test
//    fun removeFavoriteTvShow() = runTest {
//        val dummyTvShow = flowOf(detailTvShow.toEntity())
//        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)
//
//        val statusFavorite = false
//        tmdbRepository.setFavoriteTvShow(detailTvShow, statusFavorite)
//
//        val expectedResult = detailTvShow
//        expectedResult.favorited = statusFavorite
//
//        val byId =
//            tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()).asLiveData().getOrAwaitValue()
//        assertThat(byId.data, equalTo(expectedResult))
//    }
}