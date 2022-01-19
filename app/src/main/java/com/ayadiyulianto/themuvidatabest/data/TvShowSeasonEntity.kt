package com.ayadiyulianto.themuvidatabest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowSeasonEntity(
    var id: Long,
    var seasonNumber: Int,
    var airDate: String,
    var totalEpisode: Int,
    var overview: String,
    var posterURL: String
    ): Parcelable
