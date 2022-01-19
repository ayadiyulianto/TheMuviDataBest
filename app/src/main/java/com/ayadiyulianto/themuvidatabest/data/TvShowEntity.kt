package com.ayadiyulianto.themuvidatabest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowEntity (
    var id: Long,
    var title: String,
    var overview: String,
    var genres: ArrayList<String>,
    var rating: Double,
    var duration: Int,
    var seasons: ArrayList<TvShowSeasonEntity>,
    var backdropURL: String,
    var posterURL: String,
    var youtubeVideoId: String
    ): Parcelable
