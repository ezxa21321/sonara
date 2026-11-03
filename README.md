# Sonora

Демо-приложение в стиле Spotify для Android: тёмный интерфейс, экран «сейчас играет»,
плейлисты, поиск, медиатека и **настоящее воспроизведение** восьми сгенерированных
инструментальных треков (офлайн, без интернета и без чужого контента).

> Это учебный UI-клон на демо-данных. Это не Spotify и не стриминговый сервис:
> каталог, исполнители и обложки вымышлены, аудио синтезировано специально для проекта.

## Что умеет

- **Главная** — приветствие, быстрый доступ, подборки, новые релизы, исполнители.
- **Поиск** — живой поиск по трекам, исполнителям, альбомам и плейлистам, обзор жанров.
- **Медиатека** — плейлисты, альбомы, исполнители, «Любимые треки».
- **Плейлист / альбом** — большой заголовок с градиентом, список треков, play/shuffle.
- **Исполнитель** — аватар, слушатели, популярные треки, альбомы.
- **Плеер** — обложка, seek-бар, play/pause, next/prev, shuffle, repeat (off/all/one),
  лайк, очередь воспроизведения.
- **Мини-плеер** над нижней навигацией с прогрессом.
- **Фоновое воспроизведение** через `MediaSessionService` + медиа-уведомление,
  управление с гарнитуры, пауза при отключении наушников.
- Обложки рисуются процедурно (градиенты), поэтому APK работает полностью офлайн.

## Стек

- Kotlin 2.0.21, Jetpack Compose (BOM 2024.12.01), Material 3
- Navigation Compose
- Media3 ExoPlayer + Media3 Session (фоновая служба и уведомление)
- minSdk 26, targetSdk/compileSdk 35, JDK 17

## Структура

```
app/src/main/java/com/sonora/music/
├── MainActivity.kt
├── PlayerViewModel.kt          # состояние плеера, лайки, очередь
├── data/DemoData.kt            # демо-каталог: треки, альбомы, плейлисты, жанры
├── player/
│   ├── PlaybackService.kt      # MediaSessionService + ExoPlayer
│   ├── PlayerConnection.kt     # MediaController -> StateFlow
│   └── MediaItems.kt           # Track -> MediaItem (asset:///audio/...)
├── ui/
│   ├── SonoraApp.kt            # Scaffold, нижняя навигация, NavHost
│   ├── components/             # CoverArt, TrackRow, MiniPlayer, BottomBar, карточки
│   ├── navigation/Routes.kt
│   ├── screens/                # Home, Search, Library, Collection, Artist, Player
│   └── theme/Theme.kt
└── util/Format.kt

app/src/main/assets/audio/*.wav  # 8 демо-треков (сгенерированы синтезом)
```

## Сборка локально

Нужны JDK 17 и Android SDK (platform 35, build-tools 35.0.0).

```bash
# debug APK
./gradlew assembleDebug
# файл: app/build/outputs/apk/debug/app-debug.apk

# release APK (минификация, без подписи, если нет keystore.properties)
./gradlew assembleRelease
```

Подпись release-сборки: создайте `keystore.properties` в корне проекта.

```properties
storeFile=keystore.jks
storePassword=...
keyAlias=...
keyPassword=...
```

Файл уже в `.gitignore`.

## GitHub Actions

`.github/workflows/build.yml` собирает debug и release APK на каждый push/PR
(или вручную через **Run workflow**) и выкладывает их в артефакты сборки.

Для подписи release добавьте секреты репозитория:

| Секрет | Значение |
| --- | --- |
| `ANDROID_KEYSTORE_BASE64` | `base64` от файла keystore (`.jks`) |
| `ANDROID_KEYSTORE_PASSWORD` | пароль хранилища |
| `ANDROID_KEY_ALIAS` | alias ключа |
| `ANDROID_KEY_PASSWORD` | пароль ключа |

Без секретов release-APK соберётся неподписанным — это нормально для проверки сборки.

## Ограничения демо

- 8 треков, 4 вымышленных исполнителя, 5 плейлистов; всё захардкожено в `DemoData`.
- Нет авторизации, сервера, рекомендаций и загрузки своей музыки.
- Лайки и плейлисты живут только в памяти процесса (без сохранения на диск).
- Кнопки «Устройства», «Поделиться» и меню показывают демо-уведомление.
