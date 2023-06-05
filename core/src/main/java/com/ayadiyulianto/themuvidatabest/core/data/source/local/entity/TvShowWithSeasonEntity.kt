package com.ayadiyulianto.themuvidatabest.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvShowWithSeasonEntity (
    @Embedded
    var tvShow: TvShowEntity,

    @Relation(parentColumn = "tvShowId", entityColumn = "tvShowId")
    var seasons: List<SeasonEntity>
)