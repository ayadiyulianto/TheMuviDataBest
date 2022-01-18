package com.ayadiyulianto.themuvidatabest.ui.tvshows

import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy

class TvShowsViewModel : ViewModel() {

    fun getTvShows(): List<TvShowEntity> = DataDummy.generateTvShow()
}