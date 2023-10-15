package com.arvind.moviesapp.screen.playing_video

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.arvind.moviesapp.R
import com.arvind.moviesapp.domain.models.videos.ResultsItem
import com.arvind.moviesapp.domain.models.videos.VideosResponse
import com.arvind.moviesapp.screen.movie_details.viewmodel.MovieDetailsViewModel
import com.arvind.moviesapp.ui.theme.ColorBlack
import com.arvind.moviesapp.ui.theme.ColorDivider
import com.arvind.moviesapp.ui.theme.ColorWhite
import com.arvind.moviesapp.ui.theme.GrapeFruitColor
import com.arvind.moviesapp.utils.Resource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun VideoPlayerScreen(
    movieId: Int,
    navigator: DestinationsNavigator,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val videoResponse = produceState<Resource<VideosResponse>>(initialValue = Resource.Loading()) {
        value = viewModel.getMovieVideos(movieId)
    }.value

    if (videoResponse is Resource.Success) {
        DisplayMovieVideo(videoResponse.data)
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }


}

@kotlin.OptIn(ExperimentalAnimationApi::class)
@Composable
fun DisplayMovieVideo(videoResponse: VideosResponse?) {
    val playingIndex = remember {
        mutableStateOf(0)
    }

    fun onTrailerChange(index: Int) {
        playingIndex.value = index
    }
    Column {
        videoResponse?.results?.let {
            VideoPlayer(
                modifier = Modifier.weight(1f, fill = true),
                movieVideos = it,
                playingIndex = playingIndex,
                onTrailerChange = { newIndex -> onTrailerChange(newIndex) }
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f, fill = true),
            content = {
                videoResponse?.let {
                    itemsIndexed(it.results) { index, trailer ->
                        DisplayTrailers(
                            index = index,
                            trailer = trailer,
                            playingIndex = playingIndex,
                            onTrailerClicked = { newIndex -> onTrailerChange(newIndex) })
                    }
                }
            })
    }
}

@Composable
fun DisplayTrailers(
    index: Int,
    trailer: ResultsItem,
    playingIndex: State<Int>,
    onTrailerClicked: (Int) -> Unit
) {
    val currentlyPlaying = remember {
        mutableStateOf(false)
    }
    currentlyPlaying.value = index == playingIndex.value
    ConstraintLayout(modifier = Modifier
        .testTag("TrailerParent")
        .padding(10.dp)
        .wrapContentSize()
        .clickable {
            onTrailerClicked(index)
        }) {
        val (thumbnail, play, title, nowPlaying) = createRefs()
        Image(
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = trailer.key)
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.ic_placeholder)
                        crossfade(true)
                    }).build()
            ),
            contentDescription = stringResource(id = R.string.movie_video_trailer),
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = ColorWhite)
                .shadow(elevation = 20.dp)
                .constrainAs(thumbnail) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                }
        )
        if (currentlyPlaying.value) {
            Image(
                contentScale = ContentScale.Crop,
                colorFilter = if (trailer.key.isEmpty()) ColorFilter.tint(ColorWhite) else ColorFilter.tint(
                    GrapeFruitColor
                ),
                painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                contentDescription = stringResource(id = R.string.movie_video_trailer),
                modifier = Modifier
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
        }
        Text(
            text = trailer.name,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(thumbnail.top, margin = 8.dp)
                    start.linkTo(thumbnail.end, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.preferredWrapContent
                    height = Dimension.wrapContent
                },
            color = ColorWhite,
            textAlign = TextAlign.Center,
            softWrap = true,
        )
        if (currentlyPlaying.value) {
            Text(
                text = stringResource(id = R.string.movie_video_trailer),
                color = GrapeFruitColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(nowPlaying) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(thumbnail.end, margin = 8.dp)
                    bottom.linkTo(thumbnail.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.preferredWrapContent
                    height = Dimension.preferredWrapContent
                }
            )
        }
        TrailerDivider()
    }
}

@Composable
fun TrailerDivider() {
    Divider(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .testTag("Divider"),
        color = ColorDivider
    )
}

@OptIn(UnstableApi::class)
@ExperimentalAnimationApi
@Composable
fun VideoPlayer(
    movieVideos: List<ResultsItem>,
    playingIndex: State<Int>,
    onTrailerChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val visible = remember {
        mutableStateOf(true)
    }
    val videoTitle = remember {
        mutableStateOf(movieVideos[playingIndex.value].name)
    }
    val mediaItems = arrayListOf<MediaItem>()
    movieVideos.forEach {
        mediaItems.add(
            MediaItem.Builder().setUri(it.key).setMediaId(it.id).setTag(it)
                .setMediaMetadata(MediaMetadata.Builder().setDisplayTitle(it.name).build())
                .build()
        )
    }
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            this.setMediaItems(mediaItems)
            this.prepare()
            this.addListener(object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)
                    if (player.contentPosition >= 200) visible.value = false
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    onTrailerChange(this@apply.currentPeriodIndex)
                    visible.value = true
                    videoTitle.value = mediaItem?.mediaMetadata?.displayTitle.toString()
                }
            })
        }
    }

    exoPlayer.seekTo(playingIndex.value, C.TIME_UNSET)
    exoPlayer.playWhenReady = true

    LocalLifecycleOwner.current.lifecycle.addObserver(object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun resumeVideo() {
            if (exoPlayer.isPlaying.not()) {
                exoPlayer.play()
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun stopVideo() {
            exoPlayer.pause()
        }
    })

    ConstraintLayout(modifier = modifier.background(ColorBlack)) {
        val (title, videoPlayer) = createRefs()
        AnimatedVisibility(
            visible = visible.value,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250)),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = videoTitle.value,
                style = MaterialTheme.typography.h6,
                color = White,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }


        DisposableEffect(
            AndroidView(
                modifier = modifier
                    .testTag("VideoPlayer")
                    .constrainAs(videoPlayer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(
                            MATCH_PARENT,
                            MATCH_PARENT
                        )
                    }
                })
        ) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}
