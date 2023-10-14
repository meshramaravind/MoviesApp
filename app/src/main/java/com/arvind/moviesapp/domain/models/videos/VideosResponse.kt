package com.arvind.moviesapp.domain.models.videos

import com.arvind.moviesapp.domain.models.Film
import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("id")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultsItem>,
)
