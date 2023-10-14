package com.arvind.moviesapp.screen.playing_video.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.arvind.moviesapp.R
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.ui.theme.ColorDivider
import com.arvind.moviesapp.ui.theme.GrapeFruitColor

@Composable
fun VideoItem(index: Int, video: ResultsItem) {
    val currentlyPlaying = remember { mutableStateOf(true) }
    ConstraintLayout(
        modifier =
        Modifier
            .testTag("VideoParent")
            .padding(10.dp)
            .wrapContentSize()
            .background(color = Color.White)
    ) {
        val (thumbnail, play, title, tagline, nowPlaying) =
            createRefs()

        // thumbnail
        Image(
            contentScale = ContentScale.Crop,
            painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = video.key)
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.ic_placeholder)
                        crossfade(true)
                    }).build()
            ),
            contentDescription = "Thumbnail",
            modifier =
            Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(elevation = 20.dp)
                .constrainAs(thumbnail) {
                    top.linkTo(
                        parent.top,
                        margin = 10.dp
                    )
                    start.linkTo(
                        parent.start,
                        margin = 10.dp
                    )
                    bottom.linkTo(parent.bottom)
                }
        )

        // title
        Text(
            text = video.name,
            modifier =
            Modifier.constrainAs(title) {
                top.linkTo(thumbnail.top, margin = 10.dp)
                start.linkTo(
                    thumbnail.end,
                    margin = 10.dp
                )
                end.linkTo(parent.end, margin = 10.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.wrapContent
            },
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            softWrap = true,
        )

        // divider
        Divider(
            modifier =
            Modifier
                .padding(horizontal = 8.dp)
                .testTag("Divider"),
            color = ColorDivider
        )

        // show only if video is currently playing
        if (currentlyPlaying.value) {
            // play button image
            Image(
                contentScale = ContentScale.Crop,
                colorFilter =
                if (video.key.isEmpty())
                    ColorFilter.tint(Color.White)
                else
                    ColorFilter.tint(GrapeFruitColor),
                painter =
                painterResource(
                    id = R.drawable.baseline_play_arrow_24
                ),
                contentDescription = "Playing",
                modifier =
                Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .graphicsLayer {
                        clip = true
                        shadowElevation = 20.dp.toPx()
                    }
                    .constrainAs(play) {
                        top.linkTo(thumbnail.top)
                        start.linkTo(thumbnail.start)
                        end.linkTo(thumbnail.end)
                        bottom.linkTo(thumbnail.bottom)
                    }
            )

            // Now playing text
            Text(
                text = "Now Playing",
                color = Color(0xFFF50057),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier =
                Modifier.constrainAs(nowPlaying) {
                    top.linkTo(
                        title.bottom,
                        margin = 8.dp
                    )
                    start.linkTo(
                        thumbnail.end,
                        margin = 8.dp
                    )
                    bottom.linkTo(
                        thumbnail.bottom,
                        margin = 8.dp
                    )
                    end.linkTo(
                        parent.end,
                        margin = 8.dp
                    )
                    width =
                        Dimension.preferredWrapContent
                    height =
                        Dimension.preferredWrapContent
                }
            )
        }
    }
}