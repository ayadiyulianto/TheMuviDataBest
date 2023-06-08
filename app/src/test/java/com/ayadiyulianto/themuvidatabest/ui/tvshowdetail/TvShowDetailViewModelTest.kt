package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import com.ayadiyulianto.themuvidatabest.vo.Resource
import org.junit.Assert
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
    private val dummyTvShowWithSeason = DataDummy.generateDummyTvShowWithSeasonDetail()[0]
    private val showId = dummyDetailShow.tvShowId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvShowWithSeason>>

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(tmdbRepository)
        viewModel.setSelectedTvShow(showId)
    }

    @Test
    fun getTvShowDetailWithSeason(){
        val expected = MutableLiveData<Resource<TvShowWithSeason>>()
        expected.value = Resource.success(dummyTvShowWithSeason)
        Mockito.`when`(tmdbRepository.getTvShowWithSeason(showId.toString())).thenReturn(expected)

        viewModel.tvShowWithSeasonEntity.observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvShowWithSeasonEntity.value

        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun addFavoriteShow(){
        val expected = MutableLiveData<Resource<TvShowWithSeason>>()
        expected.value = Resource.success(dummyTvShowWithSeason)
        Mockito.`when`(tmdbRepository.getTvShowWithSeason(showId.toString())).thenReturn(expected)

        viewModel.setFavorite()

        viewModel.tvShowWithSeasonEntity.observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvShowWithSeasonEntity.value

        Assert.assertEquals(expectedValue, actualValue)
    }
}