package mazentas.doki.videoplayer.ui.main.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import mazentas.doki.videoplayer.ui.player.PlayerActivity
import mazentas.doki.videoplayer.ui.player.utils.PlayerApi
import mazentas.doki.videoplayer.ui.settings.navigation.navigateToSettings
import mazentas.doki.videoplayer.ui.videopicker.navigation.MediaPickerRoute
import mazentas.doki.videoplayer.ui.videopicker.navigation.mediaPickerScreen
import mazentas.doki.videoplayer.ui.videopicker.navigation.navigateToMediaFolderPickerScreen

@Serializable
data object MediaRootRoute

fun NavGraphBuilder.mediaNavGraph(
    context: Context,
    navController: NavHostController,
) {
    navigation<MediaRootRoute>(startDestination = MediaPickerRoute()) {
        mediaPickerScreen(
            onNavigateUp = navController::navigateUp,
            onPlayVideos = { uris ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uris.first()
                    putParcelableArrayListExtra(PlayerApi.API_PLAYLIST, ArrayList(uris))
                }
                context.startActivity(intent)
            },
            onFolderClick = navController::navigateToMediaFolderPickerScreen,
            onSettingsClick = navController::navigateToSettings,
        )
    }
}
