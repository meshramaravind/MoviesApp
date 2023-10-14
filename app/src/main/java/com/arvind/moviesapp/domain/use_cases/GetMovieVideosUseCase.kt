package com.arvind.moviesapp.domain.use_cases

import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.utils.Resource

class GetMovieVideosUseCase(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int): Resource<VideosResponse> {
        return repository.getMovieVideos(movieId)
    }
}