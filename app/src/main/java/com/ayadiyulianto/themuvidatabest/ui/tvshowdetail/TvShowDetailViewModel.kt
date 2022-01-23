package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowEntity
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.vo.Resource

class TvShowDetailViewModel(private val tmdbRepository: TmdbRepository): ViewModel() {

    private var tvShowData: LiveData<Resource<TvShowEntity>> = MutableLiveData()

    fun getTvShowDetail(showId: String): LiveData<Resource<TvShowEntity>>{
        tvShowData = tmdbRepository.getTvShowDetail(showId)
        return tvShowData
    }

    fun getTvShowWithSeason(showId: String): LiveData<TvShowWithSeason> = tmdbRepository.getTvShowWithSeason(showId)

    fun setFavorite() {
        val tvShowResource = tvShowData.value
        if (tvShowResource != null) {
            val tvShowDetail = tvShowResource.data
            val newState = !(tvShowDetail?.favorited ?: false)
            if (tvShowDetail != null) {
                tmdbRepository.setFavoriteTvShow(tvShowDetail, newState)
            }
        }
    }
}