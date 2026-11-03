package com.sonora.music.util

import java.util.Locale

fun formatTime(ms: Long): String {
    if (ms <= 0L) return "0:00"
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%d:%02d", minutes, seconds)
}

fun formatTime(seconds: Int): String = formatTime(seconds * 1000L)

fun formatListeners(count: Int): String {
    val formatted = String.format(Locale.US, "%,d", count).replace(',', ' ')
    return "$formatted слушателей"
}

fun formatTotalTime(seconds: Int): String {
    val minutes = seconds / 60
    return if (minutes < 60) {
        "$minutes мин"
    } else {
        val hours = minutes / 60
        val rest = minutes % 60
        "$hours ч $rest мин"
    }
}
