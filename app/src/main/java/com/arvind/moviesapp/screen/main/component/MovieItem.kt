package com.arvind.moviesapp.screen.main.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arvind.moviesapp.ui.theme.AppPrimaryColor
import com.arvind.moviesapp.ui.theme.primaryPurpleColor
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import com.arvind.moviesapp.R

@Composable
fun MovieItem(
    imageUrl: String,
    modifier: Modifier,
    onclick: () -> Unit
) {

    CoilImage(
        imageModel = imageUrl,
        shimmerParams = ShimmerParams(
            baseColor = AppPrimaryColor,
            highlightColor = primaryPurpleColor,
            durationMillis = 500,
            dropOff = 0.65F,
            tilt = 20F
        ),
        failure = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_not_available),
                    contentDescription = "no image"
                )
            }
        },
        previewPlaceholder = R.drawable.ic_placeholder,
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(duration = 1000),
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onclick() },
        contentDescription = "Movie banner"
    )

}

@Composable
@Preview(showBackground = true)
fun MovieItemPreview() {
    MovieItem(
        modifier = Modifier,
        imageUrl = "https://picsum.photos/seed/picsum/200/300",
        onclick = {}
    )
}