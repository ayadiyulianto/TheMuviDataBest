package com.ayadiyulianto.themuvidatabest.data.source.remote.entity

data class SearchEntity(
    val id: Long?,
    val name: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val mediaType: String,
    val overview: String?,
    val voteAverage: Double?,
    val releaseOrAirDate: String?
)