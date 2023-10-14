package com.arvind.moviesapp.domain.use_cases

import androidx.paging.PagingData
import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.Film
import kotlinx.coroutines.flow.Flow

class GetSimilarFilmUseCase(
    private val repository: Repository
) {
    operator fun invoke(filmId: Int): Flow<PagingData<Film>> {
        return repository.getSimilarFilms(filmId)
    }
}