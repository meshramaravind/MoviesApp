package com.arvind.moviesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arvind.moviesapp.data.paging.TrendingMoviesSource
import com.arvind.moviesapp.data.remote.MoviesAPI
import com.arvind.moviesapp.domain.models.Film
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.responses.CastDetailsApiResponse
import com.arvind.moviesapp.domain.models.responses.GenresApiResponses
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.domain.repository.RemoteDataSource
import com.arvind.moviesapp.utils.Constants.ITEMS_PER_PAGE
import com.arvind.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class RemoteDataSourceImpl(
    private val moviesAPI: MoviesAPI
) : RemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): Resource<MoviesDetails> {
        val response = try {
            moviesAPI.getMovieDetails(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown Error")
        }

        return Resource.Success(response)
    }

    override suspend fun getMovieVideos(movieId: Int): Resource<VideosResponse> {
        val response = try {
            moviesAPI.getMovieVideos(movieId)
        } catch (e: Exception) {
            return Resource.Error("Unknown Error")
        }
        return Resource.Success(response)
    }

    override suspend fun getCastDetails(filmId: Int): Resource<CastDetailsApiResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieGenres(): Resource<GenresApiResponses> {
        TODO("Not yet implemented")
    }

    override fun getPopularMovies(): Flow<PagingData<Film>> {
        TODO("Not yet implemented")
    }

    override fun getTopRatedMovies(): Flow<PagingData<Film>> {
        TODO("Not yet implemented")
    }

    override fun getTrendingMovies(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                TrendingMoviesSource(moviesAPI = moviesAPI)
            }
        ).flow
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Film>> {
        TODO("Not yet implemented")
    }

    override fun getUpcomingMovies(): Flow<PagingData<Film>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarFilm(filmId: Int): Flow<PagingData<Film>> {
        TODO("Not yet implemented")
    }
}