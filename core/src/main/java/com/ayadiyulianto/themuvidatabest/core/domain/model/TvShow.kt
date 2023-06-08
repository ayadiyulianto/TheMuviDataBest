package com.ayadiyulianto.themuvidatabest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    var tvShowId: Int,
    var name: String = "",
    var overview: String = "",
    var posterPath: String = "",
    var backdropPath: String = "",
    var voteCount: Int = 0,
    var voteAverage: Double = 0.0,
    val firstAirDate: String = "",
    val genres: String = "",
    val runtime: Int = 0,
    val youtubeTrailerId: String = "",
    var favorited: Boolean = false
) : Parcelable
