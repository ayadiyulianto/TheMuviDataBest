package com.ayadiyulianto.themuvidatabest.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.data.Resource
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.util.getOrAwaitValue
import kotlinx.coroutines.flow.flowOf
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
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShow>>>

    @Mock
    private lateinit var pagedList: List<TvShow>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(tmdbUseCase)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow= Resource.Success(pagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(1)
        val show = flowOf(dummyTvShow)
        Mockito.`when`(tmdbUseCase.getDiscoverTvShow()).thenReturn(show)

        val showEntities = viewModel.getDiscoverTvShow().getOrAwaitValue().data
        Mockito.verify(tmdbUseCase).getDiscoverTvShow()
        assertNotNull(showEntities)
        assertEquals(1, showEntities?.size)

        viewModel.getDiscoverTvShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}