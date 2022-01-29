package com.ayadiyulianto.themuvidatabest.ui.main.favorites.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
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
class FavoriteTvShowsViewModelTest {

    private lateinit var viewModel: FavoriteTvShowsViewModel

    @Mock
    private lateinit var tmdbRepository: TmdbRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowsViewModel(tmdbRepository)
    }

    @Test
    fun getTvShowFav() {
        Mockito.`when`(pagedList.size).thenReturn(1)
        val shows = MutableLiveData<PagedList<TvShowEntity>>()
        shows.value = pagedList
        Mockito.`when`(tmdbRepository.getFavoriteTvShow()).thenReturn(shows)

        val showEntities = viewModel.getTvShowFav().value
        Mockito.verify(tmdbRepository).getFavoriteTvShow()
        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.getTvShowFav().observeForever(observer)
        Mockito.verify(observer).onChanged(pagedList)
    }
}