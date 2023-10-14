package com.arvind.moviesapp.screen.movie_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.arvind.moviesapp.R
import com.arvind.moviesapp.common_components.CustomToolbar
import com.arvind.moviesapp.common_components.LoopReverseLottieLoader
import com.arvind.moviesapp.domain.models.MoviesDetails
import com.arvind.moviesapp.screen.movie_details.component.MovieImageBanner
import com.arvind.moviesapp.screen.movie_details.component.MovieInfo
import com.arvind.moviesapp.screen.movie_details.viewmodel.MovieDetailsViewModel
import com.arvind.moviesapp.utils.Constants
import com.arvind.moviesapp.utils.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    movieTitle: String,
    navigator: DestinationsNavigator,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val scrollState = rememberLazyListState()

    val details = produceState<Resource<MoviesDetails>>(initialValue = Resource.Loading()) {
        value = viewModel.getMovieDetails(movieId)
    }.value

    Scaffold(topBar = {
        CustomToolbar(title = movieTitle, onBack = {
            navigator.popBackStack()
        })
    }) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (details is Resource.Success) {
                MovieInfo(
                    scrollState = scrollState,
                    overview = details.data?.overview.toString(),
                    releaseDate = details.data?.release_date.toString(),
                    runTime = details.data?.runtime!!,
                    languages = details.data.original_language.toString(),
                    popularity = details.data.popularity!!.toInt(),
                    budget = details.data.budget.toString(),
                    revenue = details.data.revenue.toString(),
                    navigator = navigator,
                    details = details,
                    movieId = movieId,
                )

                MovieImageBanner(
                    backDroPathUrl = "${Constants.IMAGE_BASE_URL}/${details.data.backdrop_path}",
                    posterUrl = "${Constants.IMAGE_BASE_URL}/${details.data.poster_path}",
                    movieTitle = details.data.title.toString(),
                    tagline = details.data.tagline.toString(),
                    filmId = details.data.id!!,
                    rating = details.data.vote_average?.toFloat()!!,
                    voteCount = details.data.vote_count.toString(),
                    navigator = navigator,
                )
            } else {
                LoopReverseLottieLoader(lottieFile = R.raw.loader)
            }

        }
    }

}