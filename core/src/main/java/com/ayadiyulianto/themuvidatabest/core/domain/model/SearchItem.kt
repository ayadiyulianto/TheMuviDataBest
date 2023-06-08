package com.ayadiyulianto.themuvidatabest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchItem(
    val id: Int,
    val name: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val mediaType: String = "",
    val overview: String = "",
    val voteCount: Int = 0,
    val voteAverage: Double = 0.0,
    val releaseOrAirDate: String = ""
): Parcelable