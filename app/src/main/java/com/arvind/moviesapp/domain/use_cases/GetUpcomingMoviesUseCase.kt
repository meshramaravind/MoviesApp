package com.arvind.moviesapp.domain.use_cases

import androidx.paging.PagingData
import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.Film
import kotlinx.coroutines.flow.Flow

class GetUpcomingMoviesUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Film>> {
        return repository.getUpcomingMovies()
    }
}