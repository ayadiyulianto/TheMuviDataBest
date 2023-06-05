package com.ayadiyulianto.themuvidatabest.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShows")
data class TvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "tvShowId")
    var tvShowId: Int,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "posterPath")
    var posterPath: String = "",

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String = "",

    @ColumnInfo(name = "voteCount")
    val voteCount: Int = 0,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double = 0.0,

    @ColumnInfo(name = "firstAirDate")
    val firstAirDate: String = "",

    @ColumnInfo(name = "genres")
    val genres: String = "",

    @ColumnInfo(name = "runtime")
    val runtime: Int = 0,

    @ColumnInfo(name = "youtubeTrailerId")
    val youtubeTrailerId: String = "",

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false
)
