package com.ayadiyulianto.themuvidatabest.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("english_name")
    val englishName: String? = null
)

data class ProductionCountriesItem(

    @field:SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

data class GenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class VideoResults(
    @field:SerializedName("results")
    val results: List<VideosItem>? = null,
)

data class VideosItem(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("key")
    val key: String? = null,

    @field:SerializedName("site")
    val site: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("id")
    val id: String? = null,
)

fun VideoResults.getYoutubeTrailerId(): String? {
    val ytTrailer = this.results?.filter { it.site == "YouTube" && it.type == "Trailer" }
    if (ytTrailer != null && ytTrailer.isNotEmpty()) {
        return ytTrailer[0].key
    }
    val ytVideo = this.results?.filter { it.site == "YouTube" }
    if (ytVideo != null && ytVideo.isNotEmpty()) {
        return ytVideo[0].key
    }
    return null
}