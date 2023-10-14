package com.arvind.moviesapp.di

import com.arvind.moviesapp.data.repository.Repository
import com.arvind.moviesapp.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesUseCases(repository: Repository): UseCases {
        return UseCases(
            castDetailsUseCase = CastDetailsUseCase(repository),
            getMovieGenresUseCase = GetMovieGenresUseCase(repository),
            getPopularMoviesUseCase = GetPopularMoviesUseCase(repository),
            getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(repository),
            getUpcomingMoviesUseCase = GetUpcomingMoviesUseCase(repository),
            getMoviesDetailsUseCase = GetMoviesDetailsUseCase(repository),
            getMovieVideosUseCase = GetMovieVideosUseCase(repository),
            getTrendingMoviesUseCase = GetTrendingMoviesUseCase(repository),
            getNowPayingMoviesUseCase = GetNowPayingMoviesUseCase(repository),
        )
    }
}