package com.ayadiyulianto.themuvidatabest.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    val movieId: Int,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "posterPath")
    val posterPath: String? = null,

    @ColumnInfo(name = "backdropPath")
    val backdropPath: String? = null,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String? = null,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "runtime")
    val runtime: Int? = 0,

    @ColumnInfo(name = "genres")
    val genres: String? = null,

    @ColumnInfo(name = "youtubeTrailerId")
    val youtubeTrailerId: String? = null,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean? = false
)