package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TvShowEntity
import com.ayadiyulianto.themuvidatabest.util.DataDummy

class TvShowDetailViewModel: ViewModel() {

    private var showId: Long = 0L

    fun setSelectedShow(showId: Long) {
        this.showId = showId
    }

    fun getShow(): TvShowEntity {
        lateinit var show: TvShowEntity
        val showsEntity = DataDummy.generateTvShow()
        for (showEntity in showsEntity) {
            if (showEntity.id == showId) {
                show = showEntity
            }
        }
        return show
    }

}