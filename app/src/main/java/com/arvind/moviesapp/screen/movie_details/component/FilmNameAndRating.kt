package com.arvind.moviesapp.screen.movie_details.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arvind.moviesapp.ui.theme.ColorGray
import com.arvind.moviesapp.ui.theme.ColorGold
import com.arvind.moviesapp.ui.theme.PrimaryGray
import java.math.RoundingMode

@Composable
fun FilmNameAndRating(
    movieTitle: String,
    tagline: String,
    rating: Float,
    voteCount: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.83f),
            text = movieTitle,
            color = Color.White,
            style = MaterialTheme.typography.h5,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.83f),
            text = tagline,
            color = PrimaryGray,
            style = MaterialTheme.typography.caption,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = "Star ranking",
                tint = ColorGold,
                modifier = Modifier
                    .size(24.dp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.13f),
                text = rating.toBigDecimal().setScale(1, RoundingMode.CEILING).toString(),
                style = MaterialTheme.typography.h6,
                color = ColorGold
            )

            Text(
                text = "($voteCount reviews)",
                style = MaterialTheme.typography.caption,
                color = ColorGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

        }
    }

}

@Composable
@Preview(showBackground = true)
fun FilmNameAndRatingPreview() {
    FilmNameAndRating(
        movieTitle = "Knock at the Cabin",
        tagline = "Tagline",
        rating = 0.75F,
        voteCount = "5000",
    )
}