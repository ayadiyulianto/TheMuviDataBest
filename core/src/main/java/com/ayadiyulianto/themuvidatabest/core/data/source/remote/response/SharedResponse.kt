package com.ayadiyulianto.themuvidatabest.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpokenLanguagesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("english_name")
    val englishName: String? = null
)

@Keep
data class ProductionCountriesItem(

    @field:SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

@Keep
data class GenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

@Keep
data class VideoResults(
    @field:SerializedName("results")
    val results: List<VideosItem>? = null,
)

@Keep
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