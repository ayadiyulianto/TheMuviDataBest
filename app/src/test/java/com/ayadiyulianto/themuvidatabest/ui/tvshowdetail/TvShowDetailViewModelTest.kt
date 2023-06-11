package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.core.util.DataDummy
import com.ayadiyulianto.themuvidatabest.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.util.getOrAwaitValue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<Resource<TvShowWithSeason>>

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(tmdbUseCase)
        viewModel.setSelectedTvShow(dummyDetailShow)
    }

    @Test
    fun getTvShowDetailWithSeason() = runTest{
        val expected = flowOf(Resource.Success(dummyTvShowWithSeason))
        Mockito.`when`(tmdbUseCase.getTvShowWithSeason(showId.toString())).thenReturn(expected)

        viewModel.getTvShowSeasons(showId).observeForever(observer)
        Mockito.verify(observer).onChanged(expected.first())

        val expectedValue = expected.first()
        val actualValue = viewModel.getTvShowSeasons(showId).getOrAwaitValue()

        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun addFavoriteShow() = runTest{
        val expected = flowOf(Resource.Success(dummyTvShowWithSeason))
        Mockito.`when`(tmdbUseCase.getTvShowWithSeason(showId.toString())).thenReturn(expected)

        viewModel.setFavorite()

        viewModel.getTvShowSeasons(showId).observeForever(observer)
        Mockito.verify(observer).onChanged(expected.first())

        val expectedValue = expected.first()
        val actualValue = viewModel.getTvShowSeasons(showId).getOrAwaitValue()

        Assert.assertEquals(expectedValue, actualValue)
    }
}