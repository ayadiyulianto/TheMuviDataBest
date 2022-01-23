package com.ayadiyulianto.themuvidatabest.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TvShowWithSeason (
    @Embedded
    var mTvShow: TvShowEntity,

    @Relation(parentColumn = "tvShowId", entityColumn = "tvShowId")
    var mSeason: List<SeasonEntity>
)