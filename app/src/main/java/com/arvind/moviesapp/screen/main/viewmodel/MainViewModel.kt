package com.arvind.moviesapp.screen.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.arvind.moviesapp.domain.models.Film
import com.arvind.moviesapp.domain.models.Genres
import com.arvind.moviesapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {


    private var _getTrendingMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val trendingMovies: State<Flow<PagingData<Film>>> = _getTrendingMovies

    private val _getUpcomingMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val upcomingMovies: State<Flow<PagingData<Film>>> = _getUpcomingMovies

    private val _topRatedMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val topRatedMovies: State<Flow<PagingData<Film>>> = _topRatedMovies

    private val _getNowPayingMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val nowPlayingMovies: State<Flow<PagingData<Film>>> = _getNowPayingMovies

    private val _getPopularMovies = mutableStateOf<Flow<PagingData<Film>>>(emptyFlow())
    val popularMovies: State<Flow<PagingData<Film>>> = _getPopularMovies

    init {
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            _getTrendingMovies.value = useCases.getTrendingMoviesUseCase().cachedIn(
                viewModelScope
            )
        }
    }


}