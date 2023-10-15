package com.arvind.moviesapp.domain.models.videos

object VideoData {
    val videoModels = listOf<VideoModel>(
        VideoModel(
            id = 0,
            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            name = "Big Buck Bunny",
        ),
        VideoModel(
            id = 1,
            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            name = "Elephants Dream",
        ),
        VideoModel(
            id = 2,
            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            name = "Sintel",
        ),
        VideoModel(
            id = 3,
            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            name = "Subaru Out back On Street And Dirt",
        ),
        VideoModel(
            id = 4,
            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            name = "Tears Of Steel",
        )
    )
}

data class VideoModel(
    val id: Int,
    val videoUrl: String,
    val name: String,
)
