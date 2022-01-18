package com.ayadiyulianto.themuvidatabest.tvshows

import com.ayadiyulianto.themuvidatabest.ui.tvshows.TvShowsViewModel
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel
    @Before
    fun setUp() {
        viewModel = TvShowsViewModel()
    }

    @Test
    fun getTvShow() {
        val tvShowEntities = viewModel.getTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(11, tvShowEntities.size)
    }
}