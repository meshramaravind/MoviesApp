package com.arvind.moviesapp.data.remote

import com.arvind.moviesapp.BuildConfig
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.responses.FilmResponse
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.utils.Constants.STARTING_PAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("trending/movie/day")
    suspend fun getTrendingTodayMovies(
        @Query("page") page: Int = STARTING_PAGE,
        @Query("api_key") apiKey: String = BuildConfig.MOVIES_API_KEY,
        @Query("language") language: String = "en"
    ): FilmResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIES_API_KEY,
        @Query("language") language: String = "en"
    ): MoviesDetails


    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.MOVIES_API_KEY,
        @Query("language") language: String = "en",
    ): VideosResponse
}