package com.arvind.moviesapp.domain.use_cases

import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.responses.GenresApiResponses
import com.arvind.moviesapp.utils.Resource

class GetMovieGenresUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): Resource<GenresApiResponses> {
        return repository.getMovieGenres()
    }
}