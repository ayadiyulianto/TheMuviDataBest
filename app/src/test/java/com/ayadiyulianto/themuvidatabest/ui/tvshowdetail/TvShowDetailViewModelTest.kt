package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TvShowDetailEntity
import com.ayadiyulianto.themuvidatabest.data.source.TmdbRepository
import com.ayadiyulianto.themuvidatabest.util.DataDummy
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
class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowDetailViewModel
    private val dummyDetailShow= DataDummy.generateDummyTvShowDetail()[0]
    private val showId = dummyDetailShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<TvShowDetailEntity>

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(tmdbRepository)
        viewModel.getTvShowDetail(showId.toString())
    }

    @Test
    fun getTvShowDetail() {
        val showDummy = MutableLiveData<TvShowDetailEntity>()
        showDummy.value = dummyDetailShow

        Mockito.`when`(tmdbRepository.getTvShowDetail(showId.toString())).thenReturn(showDummy)

        Mockito.verify(tmdbRepository).getTvShowDetail(showId.toString())

        val showData = viewModel.getTvShowDetail(showId.toString()).value as TvShowDetailEntity

        assertNotNull(showData)

        assertEquals(dummyDetailShow.posterPath, showData.posterPath)
        assertEquals(dummyDetailShow.backdropPath, showData.backdropPath)
        assertEquals(dummyDetailShow.id, showData.id)
        assertEquals(dummyDetailShow.originalName, showData.originalName)
        assertEquals(dummyDetailShow.name, showData.name)
        assertEquals(dummyDetailShow.overview, showData.overview)
        assertEquals(dummyDetailShow.voteAverage, showData.voteAverage)
        assertEquals(dummyDetailShow.voteCount, showData.voteCount)
        assertEquals(dummyDetailShow.genres, showData.genres)
        assertEquals(dummyDetailShow.runtime, showData.runtime)
        assertEquals(dummyDetailShow.releaseDate, showData.releaseDate)
        assertEquals(dummyDetailShow.seasons, showData.seasons)

        viewModel.getTvShowDetail(showId.toString()).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyDetailShow)
    }
}