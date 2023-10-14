package com.arvind.moviesapp.domain.models.responses

import com.arvind.moviesapp.domain.models.UpcomingMovies
import com.google.gson.annotations.SerializedName

data class UpcomingMoviesApiResponses(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searches: List<UpcomingMovies>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

