package com.ayadiyulianto.themuvidatabest.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShows")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    var tvShowId: Int,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = null,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String? = null,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double? = 0.0,

    @ColumnInfo(name = "firstAirDate")
    val firstAirDate: String?,

    @ColumnInfo(name = "genres")
    val genres: String? = null,

    @ColumnInfo(name = "runtime")
    val runtime: Int? = 0,

    @ColumnInfo(name = "youtubeTrailerId")
    val youtubeTrailerId: String? = null,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean? = false
)
