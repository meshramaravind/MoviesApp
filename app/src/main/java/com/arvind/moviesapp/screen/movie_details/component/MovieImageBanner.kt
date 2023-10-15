package com.arvind.moviesapp.screen.movie_details.component


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arvind.moviesapp.R
import com.arvind.moviesapp.ui.theme.*
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
                    contentDescription = stringResource(id = R.string.poster_description),
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

//    Column(modifier = Modifier.fillMaxSize()) {
//
//        ConstraintLayout(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.33F)
//        ) {
//            val (
//                backdropImage,
//                moviePosterImage,
//            ) = createRefs()
//
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(data = backDroPathUrl).crossfade(true)
//                    .build(),
//                contentDescription = stringResource(id = R.string.description),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .shadow(
//                        elevation = 100.dp,
//                        shape = RectangleShape,
//                        ambientColor = Purple200,
//                        spotColor = Purple200,
//                        clip = false
//                    )
//                    .constrainAs(backdropImage) {
//                        top.linkTo(parent.top)
//                        bottom.linkTo(parent.bottom)
//                    }
//            )
//
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(data = posterUrl).crossfade(true).build(),
//                contentDescription = stringResource(id = R.string.poster_description),
//                contentScale = ContentScale.FillBounds,
//                modifier = Modifier
//                    .width(150.dp)
//                    .height(170.dp)
//                    .padding(horizontal = 20.dp)
//                    .shadow(
//                        elevation = 20.dp,
//                        shape = RectangleShape,
//                        ambientColor = Purple200,
//                        spotColor = Purple200,
//                        clip = false
//                    )
//                    .clip(shape = RoundedCornerShape(10.dp))
//                    .constrainAs(moviePosterImage) {
//                        top.linkTo(backdropImage.bottom)
//                        bottom.linkTo(backdropImage.bottom)
//                        start.linkTo(parent.start)
//                    }
//            )
//
//            FilmNameAndRating(
//                movieTitle = movieTitle,
//                tagline = tagline,
//                rating = rating,
//                voteCount = voteCount,
//            )
//        }
//}
}
