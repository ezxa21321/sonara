package com.sonora.music

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.sonora.music.data.Collection
import com.sonora.music.data.DemoData
import com.sonora.music.data.Track
import com.sonora.music.player.PlaybackState
import com.sonora.music.player.PlayerConnection
import com.sonora.music.player.toMediaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val connection = PlayerConnection(application)

    val playback: StateFlow<PlaybackState> = connection.state

    private val _positionMs = MutableStateFlow(0L)
    val positionMs: StateFlow<Long> = _positionMs.asStateFlow()

    private val _liked = MutableStateFlow(DemoData.initialLiked)
    val liked: StateFlow<Set<String>> = _liked.asStateFlow()

    private val _sourceTitle = MutableStateFlow("Sonora")
    val sourceTitle: StateFlow<String> = _sourceTitle.asStateFlow()

    private val _toast = MutableStateFlow<String?>(null)
    val toast: StateFlow<String?> = _toast.asStateFlow()

    init {
        viewModelScope.launch {
            while (isActive) {
                connection.controller?.let { controller ->
                    _positionMs.value = controller.currentPosition.coerceAtLeast(0L)
                }
                delay(400)
            }
        }
    }

    // ---------- Воспроизведение ----------

    fun playCollection(collection: Collection, startIndex: Int = 0) {
        playQueue(DemoData.tracksOf(collection.trackIds), startIndex, collection.title)
    }

    fun playTracks(tracks: List<Track>, startIndex: Int = 0, source: String? = null) {
        playQueue(tracks, startIndex, source)
    }

    fun playTrack(track: Track) {
        playQueue(listOf(track), 0, track.albumTitle)
    }

    private fun playQueue(tracks: List<Track>, startIndex: Int, source: String?) {
        if (tracks.isEmpty()) return
        viewModelScope.launch {
            val controller = withTimeoutOrNull(4_000) { connection.awaitController() } ?: return@launch
            controller.setMediaItems(
                tracks.map { it.toMediaItem() },
                startIndex.coerceIn(0, tracks.lastIndex),
                0L
            )
            controller.prepare()
            controller.play()
            _positionMs.value = 0L
            if (source != null) _sourceTitle.value = source
        }
    }

    fun togglePlayPause() = withController { controller ->
        if (controller.isPlaying) {
            controller.pause()
        } else {
            if (controller.playbackState == Player.STATE_IDLE) controller.prepare()
            controller.play()
        }
    }

    fun next() = withController { it.seekToNextMediaItem() }

    fun previous() = withController { controller ->
        if (controller.currentPosition > 3_000L) {
            controller.seekTo(0L)
        } else {
            controller.seekToPreviousMediaItem()
        }
    }

    fun seekTo(ms: Long) = withController { it.seekTo(ms.coerceAtLeast(0L)) }

    fun seekToQueueItem(index: Int) = withController { it.seekTo(index, 0L) }

    fun toggleShuffle() = withController { it.shuffleModeEnabled = !it.shuffleModeEnabled }

    fun cycleRepeat() = withController { controller ->
        controller.repeatMode = when (controller.repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
    }

    private inline fun withController(crossinline block: (MediaController) -> Unit) {
        viewModelScope.launch {
            val controller = connection.controller
                ?: withTimeoutOrNull(4_000) { connection.awaitController() }
                ?: return@launch
            block(controller)
        }
    }

    // ---------- Избранное ----------

    fun toggleLike(trackId: String) {
        _liked.value = _liked.value.let { current ->
            if (trackId in current) current - trackId else current + trackId
        }
    }

    fun likedTracks(): List<Track> = DemoData.tracks.filter { it.id in _liked.value }

    fun isLiked(trackId: String): Boolean = trackId in _liked.value

    fun showToast(message: String) {
        _toast.value = message
    }

    fun clearToast() {
        _toast.value = null
    }

    override fun onCleared() {
        connection.release()
        super.onCleared()
    }
}
