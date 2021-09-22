package com.dicoding.moviejetpacklast.data.source.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("overview")
    var desc: String? = null,
    @SerializedName("poster_path")
    var poster: String? = null,
    @SerializedName("backdrop_path")
    var imgPreview: String? = null
)