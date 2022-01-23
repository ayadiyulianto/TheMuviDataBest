package com.ayadiyulianto.themuvidatabest.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailEntity(
    val id: Int?,
    val title: String?,
    val backdropPath: String?,
    val genres: ArrayList<String>?,
    val voteCount: Int?,
    val overview: String?,
    val originalTitle: String?,
    val runtime: Int?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val youtubeVideoIds: List<String?>?
):Parcelable
