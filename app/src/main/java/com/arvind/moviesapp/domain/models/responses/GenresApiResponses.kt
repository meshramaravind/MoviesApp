package com.arvind.moviesapp.domain.models.responses

import android.os.Parcelable
import com.arvind.moviesapp.domain.models.Genres
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresApiResponses(
    @SerializedName("genres")
    val genres: List<Genres>
) : Parcelable
