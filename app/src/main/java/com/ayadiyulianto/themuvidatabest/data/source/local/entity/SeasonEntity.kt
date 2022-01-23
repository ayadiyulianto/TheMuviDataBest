package com.ayadiyulianto.themuvidatabest.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "seasons",
    primaryKeys = ["tvShowId", "seasonId"],
    foreignKeys = [ForeignKey(
        entity = TvShowEntity::class,
        parentColumns = ["tvShowId"],
        childColumns = ["tvShowId"]
    )],
    indices = [Index(value = ["seasonId"]), Index(value = ["tvShowId"])]
)
data class SeasonEntity(
    @NonNull
    @ColumnInfo(name = "seasonId")
    var seasonId: Int,

    @NonNull
    @ColumnInfo(name = "tvShowId")
    var tvShowId: Int,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = "",

    @ColumnInfo(name = "airDate")
    var airDate: String? = "",

    @ColumnInfo(name = "seasonNumber")
    var seasonNumber: Int? = 0,

    @ColumnInfo(name = "episodeCount")
    var episodeCount: Int? = 0,

    @ColumnInfo(name = "posterPath")
    var posterPath: String? = "",

    @ColumnInfo(name = "favorited")
    var favorited: Boolean? = false
)
