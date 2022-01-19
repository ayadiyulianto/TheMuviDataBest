package com.ayadiyulianto.themuvidatabest.tvshowdetail

import com.ayadiyulianto.themuvidatabest.ui.tvshowdetail.TvShowDetailViewModel
import com.ayadiyulianto.themuvidatabest.util.DataDummy
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowDetailViewModel
    private val dummyTvShow = DataDummy.generateTvShow()[0]
    private val tvShowId = dummyTvShow.id

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel()
    }

    @Test
    fun getShow() {
        viewModel.setSelectedShow(tvShowId)
        val tvShowEntity = viewModel.getShow()

        assertNotNull(tvShowEntity)

        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.genres, tvShowEntity.genres)
        assertEquals(dummyTvShow.duration, tvShowEntity.duration)
        assertEquals(dummyTvShow.seasons, tvShowEntity.seasons)
        assertEquals(dummyTvShow.rating.toString(), tvShowEntity.rating.toString())
        assertEquals(dummyTvShow.backdropURL, tvShowEntity.backdropURL)
        assertEquals(dummyTvShow.posterURL, tvShowEntity.posterURL)
        assertEquals(dummyTvShow.youtubeVideoId, tvShowEntity.youtubeVideoId)
    }
}