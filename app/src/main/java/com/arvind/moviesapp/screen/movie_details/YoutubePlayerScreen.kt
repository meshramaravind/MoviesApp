package com.arvind.moviesapp.screen.movie_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.arvind.moviesapp.common_components.IsLoading
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun YoutubePlayerScreen(navigator: DestinationsNavigator, youtubeCode: String) {
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val loading = remember { mutableStateOf(true) }

    val activityLifecycle = lifecycleOwner.value.lifecycle
    val context = LocalContext.current

    val youtubePlayer = remember {
        YouTubePlayerView(context).apply {
            activityLifecycle.addObserver(this)
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.apply {
                        loadOrCueVideo(activityLifecycle, youtubeCode, 0f)
                        toggleFullScreen()
                        loading.value = false
                    }
                }
            })
        }
    }

    AndroidView(
        {
            youtubePlayer
        }, modifier = Modifier
            .fillMaxSize()
    )

    IsLoading(isLoading = loading.value)
}