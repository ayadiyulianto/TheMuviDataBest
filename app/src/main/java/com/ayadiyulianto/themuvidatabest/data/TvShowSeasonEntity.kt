package com.ayadiyulianto.themuvidatabest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowSeasonEntity(
    var airDate: String?,
    var episodeCount: Int?,
    var id: Int?,
    var name: String?,
    var overview: String?,
    var posterPath: String?,
    var seasonNumber: Int?
): Parcelable
