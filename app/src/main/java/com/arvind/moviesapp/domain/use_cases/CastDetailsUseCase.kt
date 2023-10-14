package com.arvind.moviesapp.domain.use_cases

import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.responses.CastDetailsApiResponse
import com.arvind.moviesapp.utils.Resource

class CastDetailsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(filmId: Int): Resource<CastDetailsApiResponse> {
        return repository.getCastDetails(filmId = filmId)
    }
}