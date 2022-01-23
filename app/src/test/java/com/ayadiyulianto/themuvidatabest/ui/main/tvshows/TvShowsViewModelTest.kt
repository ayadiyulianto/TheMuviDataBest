package com.ayadiyulianto.themuvidatabest.ui.main.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.vo.Resource
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
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(tmdbRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow= Resource.success(pagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(1)
        val show = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        show.value = dummyTvShow

        Mockito.`when`(tmdbRepository.getDiscoverTvShow()).thenReturn(show)
        val showEntities = viewModel.getDiscoverTvShow().value?.data
        Mockito.verify(tmdbRepository).getDiscoverTvShow()
        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.getDiscoverTvShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}