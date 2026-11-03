package com.sonora.music.data

import androidx.compose.ui.graphics.Color

data class Track(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val albumId: String,
    val albumTitle: String,
    val genre: String,
    val asset: String,
    val durationSec: Int,
    val start: Color,
    val end: Color
)

data class Artist(
    val id: String,
    val name: String,
    val trackIds: List<String>,
    val monthlyListeners: Int,
    val start: Color,
    val end: Color
)

data class Album(
    val id: String,
    val title: String,
    val artistId: String,
    val artistName: String,
    val trackIds: List<String>,
    val year: Int,
    val start: Color,
    val end: Color
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String,
    val trackIds: List<String>,
    val start: Color,
    val end: Color
)

data class Genre(
    val name: String,
    val start: Color,
    val end: Color
)

/** Универсальная «коллекция» для экрана плейлиста/альбома. */
data class Collection(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String?,
    val trackIds: List<String>,
    val start: Color,
    val end: Color,
    val isLiked: Boolean = false
)

object DemoData {

    val tracks: List<Track> = listOf(
        Track(
            id = "t1", title = "Neon Drive",
            artistId = "ar1", artistName = "Aurora Lane",
            albumId = "a1", albumTitle = "Midnight City",
            genre = "Электроника", asset = "audio/neon_drive.wav", durationSec = 44,
            start = Color(0xFF6D28D9), end = Color(0xFF22D3EE)
        ),
        Track(
            id = "t2", title = "Midnight Bloom",
            artistId = "ar2", artistName = "Lumen",
            albumId = "a2", albumTitle = "Bloom",
            genre = "Chill", asset = "audio/midnight_bloom.wav", durationSec = 52,
            start = Color(0xFFEC4899), end = Color(0xFF8B5CF6)
        ),
        Track(
            id = "t3", title = "Solar Flare",
            artistId = "ar3", artistName = "Kairo",
            albumId = "a3", albumTitle = "Solar",
            genre = "Танцы", asset = "audio/solar_flare.wav", durationSec = 40,
            start = Color(0xFFF59E0B), end = Color(0xFFEF4444)
        ),
        Track(
            id = "t4", title = "Velvet Rain",
            artistId = "ar3", artistName = "Kairo",
            albumId = "a3", albumTitle = "Solar",
            genre = "Поп", asset = "audio/velvet_rain.wav", durationSec = 48,
            start = Color(0xFF10B981), end = Color(0xFF0EA5E9)
        ),
        Track(
            id = "t5", title = "Ghost Town",
            artistId = "ar4", artistName = "Static Youth",
            albumId = "a4", albumTitle = "Ghost Town",
            genre = "Инди", asset = "audio/ghost_town.wav", durationSec = 56,
            start = Color(0xFF475569), end = Color(0xFF0F172A)
        ),
        Track(
            id = "t6", title = "Aurora",
            artistId = "ar1", artistName = "Aurora Lane",
            albumId = "a1", albumTitle = "Midnight City",
            genre = "Электроника", asset = "audio/aurora.wav", durationSec = 46,
            start = Color(0xFF06B6D4), end = Color(0xFF3B82F6)
        ),
        Track(
            id = "t7", title = "Paper Planes",
            artistId = "ar2", artistName = "Lumen",
            albumId = "a2", albumTitle = "Bloom",
            genre = "Поп", asset = "audio/paper_planes.wav", durationSec = 42,
            start = Color(0xFFF97316), end = Color(0xFFDB2777)
        ),
        Track(
            id = "t8", title = "Deep Water",
            artistId = "ar4", artistName = "Static Youth",
            albumId = "a4", albumTitle = "Ghost Town",
            genre = "Джаз", asset = "audio/deep_water.wav", durationSec = 50,
            start = Color(0xFF0EA5E9), end = Color(0xFF1E3A8A)
        )
    )

    val artists: List<Artist> = listOf(
        Artist("ar1", "Aurora Lane", listOf("t1", "t6"), 2_481_903, Color(0xFF6D28D9), Color(0xFF22D3EE)),
        Artist("ar2", "Lumen", listOf("t2", "t7"), 1_204_557, Color(0xFFEC4899), Color(0xFF8B5CF6)),
        Artist("ar3", "Kairo", listOf("t3", "t4"), 3_918_221, Color(0xFFF59E0B), Color(0xFFEF4444)),
        Artist("ar4", "Static Youth", listOf("t5", "t8"), 786_402, Color(0xFF0EA5E9), Color(0xFF1E3A8A))
    )

    val albums: List<Album> = listOf(
        Album("a1", "Midnight City", "ar1", "Aurora Lane", listOf("t1", "t6"), 2025, Color(0xFF6D28D9), Color(0xFF22D3EE)),
        Album("a2", "Bloom", "ar2", "Lumen", listOf("t2", "t7"), 2024, Color(0xFFEC4899), Color(0xFF8B5CF6)),
        Album("a3", "Solar", "ar3", "Kairo", listOf("t3", "t4"), 2025, Color(0xFFF59E0B), Color(0xFFEF4444)),
        Album("a4", "Ghost Town", "ar4", "Static Youth", listOf("t5", "t8"), 2023, Color(0xFF0EA5E9), Color(0xFF1E3A8A))
    )

    val playlists: List<Playlist> = listOf(
        Playlist(
            "p1", "Мой плейлист №1",
            "Треки, которые хочется слушать снова и снова",
            listOf("t1", "t3", "t4", "t7", "t2", "t6"),
            Color(0xFF8B5CF6), Color(0xFFEC4899)
        ),
        Playlist(
            "p2", "Ночная поездка",
            "Синтвейв и неоновые огни до самого утра",
            listOf("t1", "t6", "t8", "t2"),
            Color(0xFF1E3A8A), Color(0xFF06B6D4)
        ),
        Playlist(
            "p3", "Фокус",
            "Спокойный фон для работы и учёбы",
            listOf("t4", "t8", "t2"),
            Color(0xFF0F766E), Color(0xFF84CC16)
        ),
        Playlist(
            "p4", "Тренировка",
            "Энергия на максимум",
            listOf("t3", "t5", "t1", "t7"),
            Color(0xFFDC2626), Color(0xFFF59E0B)
        ),
        Playlist(
            "p6", "Инди-волна",
            "Гитарный воздух и немного меланхолии",
            listOf("t5", "t7", "t2"),
            Color(0xFF334155), Color(0xFF0EA5E9)
        )
    )

    val genres: List<Genre> = listOf(
        Genre("Поп", Color(0xFFE8115B), Color(0xFFF59E0B)),
        Genre("Электроника", Color(0xFF0D72EA), Color(0xFF06B6D4)),
        Genre("Инди", Color(0xFF8D67AB), Color(0xFF3B82F6)),
        Genre("Chill", Color(0xFF1E8F6E), Color(0xFF84CC16)),
        Genre("Танцы", Color(0xFFDC148C), Color(0xFF7C3AED)),
        Genre("Джаз", Color(0xFFA5673F), Color(0xFFF59E0B))
    )

    val initialLiked: Set<String> = setOf("t1", "t6")

    private val trackMap: Map<String, Track> = tracks.associateBy { it.id }

    fun track(id: String?): Track? = id?.let { trackMap[it] }

    fun tracksOf(ids: List<String>): List<Track> = ids.mapNotNull { trackMap[it] }

    fun artist(id: String?): Artist? = artists.firstOrNull { it.id == id }

    fun album(id: String?): Album? = albums.firstOrNull { it.id == id }

    fun playlist(id: String?): Playlist? = playlists.firstOrNull { it.id == id }

    fun artistAlbums(artistId: String): List<Album> = albums.filter { it.artistId == artistId }

    fun totalDurationSec(trackIds: List<String>): Int = tracksOf(trackIds).sumOf { it.durationSec }

    /** Резолвит плейлист или альбом в общую модель экрана коллекции. */
    fun collection(id: String, liked: Set<String>): Collection? {
        if (id == "liked") {
            val ids = tracks.map { it.id }.filter { it in liked }
            return Collection(
                id = "liked",
                title = "Любимые треки",
                subtitle = "Плейлист",
                description = "Всё, что вы отметили сердечком",
                trackIds = ids,
                start = Color(0xFF7C3AED),
                end = Color(0xFF2563EB),
                isLiked = true
            )
        }
        playlist(id)?.let { p ->
            return Collection(
                id = p.id,
                title = p.title,
                subtitle = "Плейлист",
                description = p.description,
                trackIds = p.trackIds,
                start = p.start,
                end = p.end
            )
        }
        album(id)?.let { a ->
            return Collection(
                id = a.id,
                title = a.title,
                subtitle = "Альбом",
                description = a.artistName,
                trackIds = a.trackIds,
                start = a.start,
                end = a.end
            )
        }
        return null
    }

    fun searchTracks(query: String): List<Track> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return tracks.filter {
            it.title.lowercase().contains(q) ||
                it.artistName.lowercase().contains(q) ||
                it.albumTitle.lowercase().contains(q) ||
                it.genre.lowercase().contains(q)
        }
    }

    fun searchArtists(query: String): List<Artist> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return artists.filter { it.name.lowercase().contains(q) }
    }

    fun searchAlbums(query: String): List<Album> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return albums.filter { it.title.lowercase().contains(q) || it.artistName.lowercase().contains(q) }
    }

    fun searchPlaylists(query: String): List<Playlist> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()
        return playlists.filter { it.title.lowercase().contains(q) || it.description.lowercase().contains(q) }
    }
}
