package com.ayadiyulianto.themuvidatabest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val movieId: Int,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val releaseDate: String = "",
    val voteCount: Int = 0,
    val voteAverage: Double = 0.0,
    val runtime: Int = 0,
    val genres: String = "",
    val youtubeTrailerId: String = "",
    var favorited: Boolean = false
) : Parcelable
