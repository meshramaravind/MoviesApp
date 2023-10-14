package com.arvind.moviesapp.screen.movie_details.component


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arvind.moviesapp.R
import com.arvind.moviesapp.ui.theme.AppBarExpendedHeight
import com.arvind.moviesapp.ui.theme.AppPrimaryColor
import com.arvind.moviesapp.ui.theme.primaryDark
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun MovieImageBanner(
    backDroPathUrl: String,
    posterUrl: String,
    movieTitle: String,
    tagline: String,
    filmId: Int,
    rating: Float,
    voteCount: String,
    navigator: DestinationsNavigator
) {

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = AppPrimaryColor,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
            )
    ) {
        Column {
            Box(modifier = Modifier.padding(horizontal = 15.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(backDroPathUrl).crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.description),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(start = 60.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                )

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(posterUrl).crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.description),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(120.dp)
                        .height(160.dp)
                        .align(Alignment.CenterStart)
                        .clip(shape = RoundedCornerShape(10.dp))
                )
            }

            FilmNameAndRating(
                movieTitle = movieTitle,
                tagline = tagline,
                rating = rating,
                voteCount = voteCount,
            )
        }
    }
}
