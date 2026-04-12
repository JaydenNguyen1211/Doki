package mazentas.doki.videoplayer.ui.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import mazentas.doki.videoplayer.ui.settings.Setting
import mazentas.doki.videoplayer.ui.settings.SettingsScreen

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = navOptions { launchSingleTop = true }) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(onNavigateUp: () -> Unit, onItemClick: (Setting) -> Unit) {
    composable(route = settingsNavigationRoute) {
        SettingsScreen(onNavigateUp = onNavigateUp, onItemClick = onItemClick)
    }
}
