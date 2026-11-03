package com.sonora.music.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.sonora.music.data.Track

fun Track.toMediaItem(): MediaItem = MediaItem.Builder()
    .setMediaId(id)
    .setUri("asset:///$asset")
    .setMediaMetadata(
        MediaMetadata.Builder()
            .setTitle(title)
            .setArtist(artistName)
            .setAlbumTitle(albumTitle)
            .setGenre(genre)
            .setIsPlayable(true)
            .setIsBrowsable(false)
            .build()
    )
    .build()
