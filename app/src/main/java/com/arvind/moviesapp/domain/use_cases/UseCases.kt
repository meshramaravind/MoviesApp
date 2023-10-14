package com.arvind.moviesapp.domain.use_cases

class UseCases(
    val getMoviesDetailsUseCase: GetMoviesDetailsUseCase,
    val getMovieVideosUseCase: GetMovieVideosUseCase,
    val castDetailsUseCase: CastDetailsUseCase,
    val getMovieGenresUseCase: GetMovieGenresUseCase,
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    val getNowPayingMoviesUseCase: GetNowPayingMoviesUseCase,
)