package com.arvind.moviesapp.screen.movie_details.viewmodel

import androidx.lifecycle.ViewModel
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.domain.use_cases.UseCases
import com.arvind.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    suspend fun getMovieDetails(movieId: Int): Resource<MoviesDetails> {
        val result = useCases.getMoviesDetailsUseCase(movieId)
        Timber.d(result.data.toString())
        return result
    }

    suspend fun getMovieVideos(movieId: Int): Resource<VideosResponse> {
        val result = useCases.getMovieVideosUseCase(movieId)
        Timber.d(result.data.toString())
        return result
    }
}