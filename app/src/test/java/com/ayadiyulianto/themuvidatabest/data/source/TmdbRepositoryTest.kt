package com.ayadiyulianto.themuvidatabest.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ayadiyulianto.themuvidatabest.data.source.remote.RemoteDataSource
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.LiveDataTestUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

class TmdbRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val tmdbRepository = FakeTmdbRepository(remote)

    private val listMovieResponses = DataDummy.generateDummyMovieResponse()
    private val listTvShowResponses = DataDummy.generateDummyTvShowResponse()

    private val movieId = listMovieResponses[0].id
    private val tvShowId = listTvShowResponses[0].id

    private val movieDetail = DataDummy.generateDummyMovieDetailResponse()[0]
    private val tvShowDetail = DataDummy.generateDummyTvShowDetailResponse()[0]

    @Test
    fun getMovies() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[0] as RemoteDataSource.CallbackLoadDiscoverMovie).onMoviesRecieved(listMovieResponses)
                null
            }.`when`(remote).getDiscoverMovie(any())
        }

        val dataListMovie = LiveDataTestUtil.getValue(tmdbRepository.getDiscoverMovies())

        runBlocking {
            verify(remote).getDiscoverMovie(any())
        }

        assertNotNull(dataListMovie)
        assertEquals(listMovieResponses.size.toLong(), dataListMovie.size.toLong())
    }

    @Test
    fun getTvShows() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[0] as RemoteDataSource.CallbackLoadDiscoverTvShow).onTvShowRecieved(listTvShowResponses)
                null
            }.`when`(remote).getDiscoverTvShow(any())
        }

        val dataListTvShow = LiveDataTestUtil.getValue(tmdbRepository.getDiscoverTvShow())

        runBlocking {
            verify(remote).getDiscoverTvShow(any())
        }

        assertNotNull(dataListTvShow)
        assertEquals(listTvShowResponses.size.toLong(), dataListTvShow.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[1] as RemoteDataSource.CallbackLoadMovieDetail).onMovieDetailsRecieved(movieDetail)
                null
            }.`when`(remote).getMovie(eq(movieId.toString()), any())
        }

        val dataMovie = LiveDataTestUtil.getValue(tmdbRepository.getMovieDetail(movieId.toString()))

        runBlocking {
            verify(remote).getMovie(eq(movieId.toString()), any())
        }

        assertNotNull(dataMovie)
        assertEquals(movieDetail.id, dataMovie.id)
    }

    @Test
    fun getTvShowDetail() {
        runBlocking {
            doAnswer {invocation ->
                (invocation.arguments[1] as RemoteDataSource.CallbackLoadTvShowDetail).onTvShowDetailsRecieved(tvShowDetail)
                null
            }.`when`(remote).getTvShow(eq(tvShowId.toString()), any())
        }

        val dataTvShow = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(tvShowId.toString()))

        runBlocking {
            verify(remote).getTvShow(eq(tvShowId.toString()), any())
        }

        assertNotNull(dataTvShow)
        assertEquals(tvShowDetail.id, dataTvShow.id)
    }
}