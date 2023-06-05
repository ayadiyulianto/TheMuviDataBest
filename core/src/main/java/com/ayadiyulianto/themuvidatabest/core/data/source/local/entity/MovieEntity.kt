package com.ayadiyulianto.themuvidatabest.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val movieId: Int,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "overview")
    val overview: String = "",

    @ColumnInfo(name = "posterPath")
    val posterPath: String = "",

    @ColumnInfo(name = "backdropPath")
    val backdropPath: String = "",

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String = "",

    @ColumnInfo(name = "voteCount")
    val voteCount: Int = 0,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double = 0.0,

    @ColumnInfo(name = "runtime")
    val runtime: Int = 0,

    @ColumnInfo(name = "genres")
    val genres: String = "",

    @ColumnInfo(name = "youtubeTrailerId")
    val youtubeTrailerId: String = "",

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false
)