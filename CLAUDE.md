# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Debug build
./gradlew build

# Release APK
./gradlew assembleRelease

# Unit tests
./gradlew test

# Single test class
./gradlew test --tests "com.example.videoplayer.ExampleUnitTest"

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Lint
./gradlew ktlint

# Auto-format
./gradlew ktlintFormat
```

## Architecture

Clean Architecture with MVVM, fully Kotlin + Jetpack Compose (Material 3). DI via Hilt. Three main activities: `SplashActivity` → `MainActivity` → `PlayerActivity`.

**Layers** (package: `mazentas.doki.videoplayer`):

- **`ui/`** — Compose screens, ViewModels, navigation. Sub-packages: `main/`, `player/`, `splash/`, `settings/`, `videopicker/`, `designsystem/`, `theme/`, `composables/`
- **`domain/`** — Use cases (`GetSortedFoldersUseCase`, `GetSortedMediaUseCase`, etc.)
- **`data/`** — Repository implementations (`LocalMediaRepository`, `LocalPreferencesRepository`) + mappers from DB entities to domain models
- **`database/`** — Room ORM: entities for media files, directories, playback state, stream info. 4 schema versions with migrations. Schemas exported to `/app/schemas/`
- **`datastore/`** — DataStore for app preferences (`AppPreferencesDataSource`) and player preferences (`PlayerPreferencesDataSource`)
- **`media/`** — ExoPlayer (Media3) integration, `LocalMediaService` for background playback, `LocalMediaSynchronizer` for file scanning
- **`common/`** — Coroutine dispatchers DI module, extension functions

## Navigation

Two typed-safe navigation graphs using `@Serializable` data objects:
- **Media graph**: `MediaPickerScreen` (start) → `MediaFolderPickerScreen`
- **Settings graph**: multiple settings screens

Defined in `ui/main/` alongside `MainNavHost`.

## Key Libraries

| Purpose | Library |
|---|---|
| Media playback | AndroidX Media3 1.9.0 (ExoPlayer + DASH/HLS/RTSP) |
| UI | Jetpack Compose BOM 2025.12.01, Material3 1.5.0-alpha11 |
| DI | Hilt 2.57.1 |
| Database | Room 2.8.4 (KSP) |
| Preferences | DataStore 1.2.0 |
| Image loading | Coil 2.7.0 |
| Logging | Timber 5.0.1 |
| Permissions | Accompanist Permissions 0.37.3 |
| Serialization | Kotlinx Serialization JSON 1.9.0 |
| Crash reporting | Firebase Crashlytics + Analytics |

## SDK & Java

- `minSdk` 24, `targetSdk` / `compileSdk` 36, Java 17
- Kotlin 2.3.0, Android Gradle Plugin 9.1.0
- Version catalog: `gradle/libs.versions.toml`

## Permissions & Intent Filters

The app handles `READ_MEDIA_VIDEO` (API 33+) and `READ_EXTERNAL_STORAGE` (API ≤32). `PlayerActivity` declares `FOREGROUND_SERVICE_MEDIA_PLAYBACK` and handles deep links for streaming protocols (RTSP, RTMP, HLS, etc.) and video MIME types via intent filters.

## Fake Repositories

`data/repository/` contains `Fake*` implementations of each repository interface — used for Compose previews and tests without needing real media or storage.
