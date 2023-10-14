package com.arvind.moviesapp.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import com.arvind.moviesapp.R
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.arvind.moviesapp.common_components.LoopReverseLottieLoader
import com.arvind.moviesapp.domain.models.Film
import com.arvind.moviesapp.screen.destinations.MovieDetailsScreenDestination
import com.arvind.moviesapp.screen.main.component.MovieItem
import com.arvind.moviesapp.screen.main.viewmodel.MainViewModel
import com.arvind.moviesapp.ui.theme.GrapeFruitColor
import com.arvind.moviesapp.utils.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException

@Destination(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {

        val trendingMovies = viewModel.trendingMovies.value.collectAsLazyPagingItems()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = "Trending Today",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            TextButton(
                onClick = {

                },
                shape = RoundedCornerShape(size = 30.dp),
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
            ) {
                Text(
                    text = "see all",
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
        }

        GridMovieItems(
            navigator = navigator,
            pagingItems = trendingMovies,
            onErrorClick = {
                viewModel.trendingMovies
            }
        )
    }

}

@Composable
fun GridMovieItems(
    navigator: DestinationsNavigator,
    pagingItems: LazyPagingItems<Film>,
    onErrorClick: () -> Unit
) {

    when (pagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            LoopReverseLottieLoader(lottieFile = R.raw.loader)
        }

        is LoadState.NotLoading -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(10.dp)
            ) {
                items(pagingItems.itemCount) { index ->
                    val imagePath =
                        "${Constants.IMAGE_BASE_URL}/${pagingItems[index]!!.poster_path}"
                    MovieItem(
                        imageUrl = imagePath,
                        modifier = Modifier
                            .width(150.dp)
                            .height(220.dp)
                    ) {
                        navigator.navigate(
                            MovieDetailsScreenDestination(
                                pagingItems[index]!!.id,
                                pagingItems[index]!!.title
                            )
                        )
                    }
                }
            }

        }
        is LoadState.Error -> {
            val error = pagingItems.loadState.refresh as LoadState.Error
            val errorMessage = when (error.error) {
                is HttpException -> "Sorry, Something went wrong!\nTap to retry"
                is IOException -> "Connection failed. Tap to retry!"
                else -> "Failed! Tap to retry!"
            }
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(161.25.dp) // maintain the vertical space between two categories
                    .clickable {
                        onErrorClick()
                    }
            ) {
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = GrapeFruitColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        else -> {
        }
    }
}
