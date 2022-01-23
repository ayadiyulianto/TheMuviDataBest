package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
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

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(tmdbRepository)
    }

    @Test
    fun getTvShowDetail() {
        val showDetailDummy = MutableLiveData<Resource<TvShowEntity>>()
        val resource = Resource.success(dummyDetailShow)
        showDetailDummy.value = resource
        Mockito.`when`(tmdbRepository.getTvShowDetail(showId.toString())).thenReturn(showDetailDummy)

        val observer = Mockito.mock(Observer::class.java) as Observer<Resource<TvShowEntity>>
        viewModel.getTvShowDetail(showId.toString()).observeForever(observer)
        Mockito.verify(observer).onChanged(resource)
    }

    @Test
    fun getTvShowDetailWithSeason(){
        val showDetailDummy = MutableLiveData<TvShowWithSeason>()
        showDetailDummy.value = dummyTvShowWithSeason
        Mockito.`when`(tmdbRepository.getTvShowWithSeason(showId.toString())).thenReturn(showDetailDummy)

        val observer = Mockito.mock(Observer::class.java) as Observer<TvShowWithSeason>
        viewModel.getTvShowWithSeason(showId.toString()).observeForever(observer)
    }

    @Test
    fun addFavoriteShow(){
        val showDetailDummy = MutableLiveData<Resource<TvShowEntity>>()
        val resource = Resource.success(dummyDetailShow)
        showDetailDummy.value = resource
        Mockito.`when`(tmdbRepository.getTvShowDetail(showId.toString())).thenReturn(showDetailDummy)

        val statusFavorite = true
        tmdbRepository.setFavoriteTvShow(dummyDetailShow, statusFavorite)

        val expectedResult = dummyDetailShow
        expectedResult.favorited = statusFavorite
        viewModel.setFavorite()

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(dummyDetailShow.tvShowId.toString()))
        MatcherAssert.assertThat(byId.data, IsEqual.equalTo(expectedResult))
    }

    @Test
    fun removeFavoriteShow(){
        val showDetailDummy = MutableLiveData<Resource<TvShowEntity>>()
        val resource = Resource.success(dummyDetailShow)
        showDetailDummy.value = resource
        Mockito.`when`(tmdbRepository.getTvShowDetail(showId.toString())).thenReturn(showDetailDummy)

        val statusFavorite = false
        tmdbRepository.setFavoriteTvShow(dummyDetailShow, statusFavorite)

        val expectedResult = dummyDetailShow
        expectedResult.favorited = statusFavorite
        viewModel.setFavorite()

        val byId = LiveDataTestUtil.getValue(tmdbRepository.getTvShowDetail(dummyDetailShow.tvShowId.toString()))
        MatcherAssert.assertThat(byId.data, IsEqual.equalTo(expectedResult))
    }
}