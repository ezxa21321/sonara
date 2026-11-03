package com.sonora.music.player

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PlaybackState(
    val isConnected: Boolean = false,
    val currentId: String? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val durationMs: Long = 0L,
    val shuffle: Boolean = false,
    val repeatMode: Int = Player.REPEAT_MODE_OFF,
    val queueIds: List<String> = emptyList(),
    val index: Int = -1,
    val hasNext: Boolean = false,
    val hasPrevious: Boolean = false
)

/**
 * Тонкая обёртка над MediaController: держит соединение со службой
 * и публикует снимок состояния плеера в StateFlow.
 */
class PlayerConnection(context: Context) {

    private val appContext = context.applicationContext

    private val controllerFuture = MediaController.Builder(
        appContext,
        SessionToken(appContext, ComponentName(appContext, PlaybackService::class.java))
    ).buildAsync()

    private val _state = MutableStateFlow(PlaybackState())
    val state: StateFlow<PlaybackState> = _state.asStateFlow()

    private val ready = CompletableDeferred<MediaController>()

    var controller: MediaController? = null
        private set

    private val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            publish(player)
        }
    }

    init {
        controllerFuture.addListener(
            {
                val controller = runCatching { controllerFuture.get() }.getOrNull()
                if (controller != null) {
                    this.controller = controller
                    controller.addListener(listener)
                    publish(controller)
                    if (!ready.isCompleted) ready.complete(controller)
                }
            },
            ContextCompat.getMainExecutor(appContext)
        )
    }

    suspend fun awaitController(): MediaController = ready.await()

    fun publish(player: Player = controller ?: return) {
        _state.value = PlaybackState(
            isConnected = true,
            currentId = player.currentMediaItem?.mediaId,
            isPlaying = player.isPlaying,
            isBuffering = player.playbackState == Player.STATE_BUFFERING,
            durationMs = player.duration.coerceAtLeast(0L),
            shuffle = player.shuffleModeEnabled,
            repeatMode = player.repeatMode,
            queueIds = List(player.mediaItemCount) { index -> player.getMediaItemAt(index).mediaId },
            index = player.currentMediaItemIndex,
            hasNext = player.hasNextMediaItem(),
            hasPrevious = player.hasPreviousMediaItem()
        )
    }

    fun release() {
        controller?.removeListener(listener)
        MediaController.releaseFuture(controllerFuture)
    }
}
