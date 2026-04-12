package mazentas.doki.videoplayer.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import mazentas.doki.videoplayer.ui.settings.Setting
import mazentas.doki.videoplayer.ui.settings.navigation.aboutPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.appearancePreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.audioPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.decoderPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.folderPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.librariesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.mediaLibraryPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToAboutPreferences
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToAppearancePreferences
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToAudioPreferences
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToDecoderPreferences
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToFolderPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToLibraries
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToMediaLibraryPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToPlayerPreferences
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToSubtitlePreferences
import mazentas.doki.videoplayer.ui.settings.navigation.playerPreferencesScreen
import mazentas.doki.videoplayer.ui.settings.navigation.settingsNavigationRoute
import mazentas.doki.videoplayer.ui.settings.navigation.settingsScreen
import mazentas.doki.videoplayer.ui.settings.navigation.subtitlePreferencesScreen

const val SETTINGS_ROUTE = "settings_nav_route"

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = settingsNavigationRoute,
        route = SETTINGS_ROUTE,
    ) {
        settingsScreen(
            onNavigateUp = navController::navigateUp,
            onItemClick = { setting ->
                when (setting) {
                    Setting.APPEARANCE -> navController.navigateToAppearancePreferences()
                    Setting.MEDIA_LIBRARY -> navController.navigateToMediaLibraryPreferencesScreen()
                    Setting.PLAYER -> navController.navigateToPlayerPreferences()
                    Setting.DECODER -> navController.navigateToDecoderPreferences()
                    Setting.AUDIO -> navController.navigateToAudioPreferences()
                    Setting.SUBTITLE -> navController.navigateToSubtitlePreferences()
                    Setting.ABOUT -> navController.navigateToAboutPreferences()
                }
            },
        )
        appearancePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        mediaLibraryPreferencesScreen(
            onNavigateUp = navController::navigateUp,
            onFolderSettingClick = navController::navigateToFolderPreferencesScreen,
        )
        folderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        playerPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        decoderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        audioPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        subtitlePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        aboutPreferencesScreen(
            onLibrariesClick = navController::navigateToLibraries,
            onNavigateUp = navController::navigateUp,
        )
        librariesScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}