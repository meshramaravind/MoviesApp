package com.arvind.moviesapp.domain.use_cases

import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.utils.Resource

class GetMoviesDetailsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(filmId: Int): Resource<MoviesDetails> {
        return repository.getMoviesDetails(filmId)
    }


}