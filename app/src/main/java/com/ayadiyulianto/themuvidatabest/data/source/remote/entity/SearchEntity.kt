package com.ayadiyulianto.themuvidatabest.data.source.remote.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchEntity(
    val id: Int,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val mediaType: String,
    val overview: String?,
    val voteAverage: Double?,
    val releaseOrAirDate: String?
): Parcelable