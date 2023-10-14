package com.arvind.moviesapp.data.repository

import androidx.paging.PagingData
import com.arvind.moviesapp.domain.models.Film
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.responses.CastDetailsApiResponse
import com.arvind.moviesapp.domain.models.responses.GenresApiResponses
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.domain.repository.RemoteDataSource
import com.arvind.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource
) {
    suspend fun getMoviesDetails(movieId: Int): Resource<MoviesDetails> {
        return remote.getMovieDetails(movieId = movieId)
    }

    suspend fun getMovieVideos(movieId: Int): Resource<VideosResponse> {
        return remote.getMovieVideos(movieId = movieId)
    }

    suspend fun getMovieGenres(): Resource<GenresApiResponses> {
        return remote.getMovieGenres()
    }

    fun getPopularMovies(): Flow<PagingData<Film>> {
        return remote.getPopularMovies()
    }

    fun getTopRatedMovies(): Flow<PagingData<Film>> {
        return remote.getTopRatedMovies()
    }

    fun getTrendingMovies(): Flow<PagingData<Film>> {
        return remote.getTrendingMovies()
    }

    fun getNowPlayingMovies(): Flow<PagingData<Film>> {
        return remote.getNowPlayingMovies()
    }

    fun getUpcomingMovies(): Flow<PagingData<Film>> {
        return remote.getUpcomingMovies()
    }

    fun getSimilarFilms(filmId: Int): Flow<PagingData<Film>> {
        return remote.getSimilarFilm(filmId = filmId)
    }

    suspend fun getCastDetails(filmId: Int): Resource<CastDetailsApiResponse> {
        return remote.getCastDetails(filmId = filmId)
    }

}