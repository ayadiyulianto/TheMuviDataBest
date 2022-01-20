package com.ayadiyulianto.themuvidatabest.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<List<TvShowEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(tmdbRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow= DataDummy.generateDummyTvShow()
        val shows = MutableLiveData<List<TvShowEntity>>()
        shows.value = DataDummy.generateDummyTvShow()

        Mockito.`when`(tmdbRepository.getDiscoverTvShow()).thenReturn(shows)

        val dataListShow = viewModel.getDiscoverTvShow().value
        Mockito.verify(tmdbRepository).getDiscoverTvShow()
        assertNotNull(dataListShow)
        assertEquals(10, dataListShow?.size)

        viewModel.getDiscoverTvShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}