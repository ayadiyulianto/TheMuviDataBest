package com.ayadiyulianto.themuvidatabest.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ayadiyulianto.themuvidatabest.data.TmdbRepository
import com.ayadiyulianto.themuvidatabest.data.source.local.entity.TvShowWithSeason
import com.ayadiyulianto.themuvidatabest.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {

    val tvShowId = MutableLiveData<String>()

    fun setSelectedTvShow(courseId: Int) {
        this.tvShowId.value = courseId.toString()
    }

    var tvShowWithSeason: LiveData<Resource<TvShowWithSeason>> = Transformations.switchMap(tvShowId) { showId ->
        tmdbRepository.getTvShowWithSeason(showId)
    }

    fun setFavorite(): Boolean {
        val tvShowResource = tvShowWithSeason.value
        if (tvShowResource != null) {
            val tvShowWithSeason = tvShowResource.data
            if (tvShowWithSeason != null) {
                val tvShowEntity = tvShowWithSeason.mTvShow
                val newState = !tvShowEntity.favorited
                tmdbRepository.setFavoriteTvShow(tvShowEntity, newState)
                return newState
            }
        }
        return false
    }
}