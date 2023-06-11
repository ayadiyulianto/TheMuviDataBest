package com.ayadiyulianto.themuvidatabest.favorite.ui.main.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayadiyulianto.themuvidatabest.core.domain.model.TvShow
import com.ayadiyulianto.themuvidatabest.core.domain.usecase.TmdbUseCase
import com.ayadiyulianto.themuvidatabest.favorite.util.MainDispatcherRule
import com.ayadiyulianto.themuvidatabest.favorite.util.getOrAwaitValue
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
class FavoriteTvShowsViewModelTest {

    private lateinit var viewModel: FavoriteTvShowsViewModel

    @Mock
    private lateinit var tmdbUseCase: TmdbUseCase

    @Mock
    private lateinit var observer: Observer<List<TvShow>>

    @Mock
    private lateinit var pagedList: List<TvShow>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowsViewModel(tmdbUseCase)
    }

    @Test
    fun getTvShowFav() {
        Mockito.`when`(pagedList.size).thenReturn(1)
        val shows = flowOf(pagedList)
        Mockito.`when`(tmdbUseCase.getFavoriteTvShow()).thenReturn(shows)

        val showEntities = viewModel.getTvShowFav().getOrAwaitValue()
        Mockito.verify(tmdbUseCase).getFavoriteTvShow()
        assertNotNull(showEntities)
        assertEquals(1, showEntities.size)

        viewModel.getTvShowFav().observeForever(observer)
        Mockito.verify(observer).onChanged(pagedList)
    }
}