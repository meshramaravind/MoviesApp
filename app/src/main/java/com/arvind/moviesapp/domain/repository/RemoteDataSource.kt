package com.arvind.moviesapp.domain.repository

import androidx.paging.PagingData
import com.arvind.moviesapp.domain.models.Film
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.responses.CastDetailsApiResponse
import com.arvind.moviesapp.domain.models.responses.GenresApiResponses
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<Film>>
    fun getTopRatedMovies(): Flow<PagingData<Film>>
    fun getTrendingMovies(): Flow<PagingData<Film>>
    fun getNowPlayingMovies(): Flow<PagingData<Film>>
    fun getUpcomingMovies(): Flow<PagingData<Film>>
    fun getSimilarFilm(filmId: Int): Flow<PagingData<Film>>


    suspend fun getMovieDetails(movieId: Int): Resource<MoviesDetails>
    suspend fun getCastDetails(filmId: Int): Resource<CastDetailsApiResponse>

    suspend fun getMovieVideos(movieId: Int): Resource<VideosResponse>


    suspend fun getMovieGenres(): Resource<GenresApiResponses>


}