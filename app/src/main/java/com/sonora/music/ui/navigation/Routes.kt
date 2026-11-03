package com.sonora.music.ui.navigation

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val COLLECTION = "collection/{id}"
    const val ARTIST = "artist/{id}"
    const val PLAYER = "player"

    fun collection(id: String) = "collection/$id"
    fun artist(id: String) = "artist/$id"
}
