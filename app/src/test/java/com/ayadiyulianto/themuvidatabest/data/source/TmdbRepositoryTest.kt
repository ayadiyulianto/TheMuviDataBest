package com.ayadiyulianto.themuvidatabest.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.LocalDataSource
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.MovieEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.util.AppExecutors
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.LiveDataTestUtil
import com.ayadiyulianto.themuvidatabest.util.PagedListUtil
import com.ayadiyulianto.themuvidatabest.vo.Resource
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class TmdbRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val tmdbRepository = FakeTmdbRepository(remote, local, appExecutors)

    private val listMovie = DataDummy.generateDummyMovie()
    private val listTvShow = DataDummy.generateDummyTvShow()

    private val movieId = listMovie[0].movieId
    private val tvShowId = listTvShow[0].tvShowId

    private val detailMovie = DataDummy.generateDummyMovieDetail()[0]
    private val detailTvShow = DataDummy.generateDummyTvShowDetail()[0]
    private val detailTvShowWithSeason = DataDummy.generateDummyTvShowWithSeasonDetail()[0]

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        tmdbRepository.getDiscoverMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getAllMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getAllTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShow()).thenReturn(dataSourceFactory)
        tmdbRepository.getDiscoverTvShow()

        val showEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllTvShow()
        assertNotNull(showEntity.data)
        assertEquals(listTvShow.size.toLong(), showEntity.data?.size?.toLong())
    }

    @Test
    fun getMovieDetails() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = detailMovie
        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(detailMovie.movieId.toString()))
        verify(local).getMovieById(movieId.toString())
        assertNotNull(movieEntity)
        assertEquals(detailMovie.movieId, movieEntity.data?.movieId)
    }

    @Test
    fun getTvShowDetail() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = detailTvShow
        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)

        val showEntity = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()))
        verify(local).getTvShowById(tvShowId.toString())
        assertNotNull(showEntity)
        assertEquals(detailTvShow.tvShowId, showEntity.data?.tvShowId)
    }

    @Test
    fun getTvShowDetailWithSeason() {
        val dummyTvShowWithSeason = MutableLiveData<TvShowWithSeason>()
        dummyTvShowWithSeason.value = detailTvShowWithSeason
        `when`(local.getTvShowWithSeason(tvShowId.toString())).thenReturn(dummyTvShowWithSeason)

        val showEntity = LiveDataTestUtil.getValue(tmdbRepository.getTvShowWithSeason(detailTvShowWithSeason.mTvShow.tvShowId.toString()))
        verify(local).getTvShowWithSeason(tvShowId.toString())
        assertNotNull(showEntity)
        assertNotNull(showEntity.data?.mTvShow)
        assertNotNull(showEntity.data?.mSeason)
        assertEquals(detailTvShow.tvShowId, showEntity.data?.mTvShow?.tvShowId)
    }

    @Test
    fun getListFavoriteMovie(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        tmdbRepository.getFavoriteMovie()

        val movieEntity = Resource.success((PagedListUtil.mockPagedList((DataDummy.generateDummyMovie()))))
        verify(local).getFavoriteMovie()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getListFavoriteTvShow(){
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        tmdbRepository.getFavoriteTvShow()

        val showEntity = Resource.success((PagedListUtil.mockPagedList((DataDummy.generateDummyTvShow()))))
        verify(local).getFavoriteTvShow()
        assertNotNull(showEntity.data)
        assertEquals(listTvShow.size.toLong(), showEntity.data?.size?.toLong())
    }

    @Test
    fun addFavoriteMovie() {
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = detailMovie
        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)

        val statusFavorite = true
        tmdbRepository.setFavoriteMovie(detailMovie, statusFavorite)

        val expectedResult = detailMovie
        expectedResult.favorited = statusFavorite

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(detailMovie.movieId.toString()))
        assertThat(byId.data, equalTo(expectedResult))
    }

    @Test
    fun removeFavoriteMovie(){
        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = detailMovie
        `when`(local.getMovieById(movieId.toString())).thenReturn(dummyMovie)

        val statusFavorite = false
        tmdbRepository.setFavoriteMovie(detailMovie, statusFavorite)

        val expectedResult = detailMovie
        expectedResult.favorited = statusFavorite

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(detailMovie.movieId.toString()))
        assertThat(byId.data, equalTo(expectedResult))
    }

    @Test
    fun addFavoriteTvShow(){
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = detailTvShow
        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)

        val statusFavorite = true
        tmdbRepository.setFavoriteTvShow(detailTvShow, statusFavorite)

        val expectedResult = detailTvShow
        expectedResult.favorited = statusFavorite

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()))
        assertThat(byId.data, equalTo(expectedResult))
    }

    @Test
    fun removeFavoriteTvShow(){
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = detailTvShow
        `when`(local.getTvShowById(tvShowId.toString())).thenReturn(dummyTvShow)

        val statusFavorite = false
        tmdbRepository.setFavoriteTvShow(detailTvShow, statusFavorite)

        val expectedResult = detailTvShow
        expectedResult.favorited = statusFavorite

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(detailTvShow.tvShowId.toString()))
        assertThat(byId.data, equalTo(expectedResult))
    }
}