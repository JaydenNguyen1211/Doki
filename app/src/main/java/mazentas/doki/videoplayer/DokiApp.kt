package mazentas.doki.videoplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mazentas.doki.videoplayer.common.di.ApplicationScope
import mazentas.doki.videoplayer.data.repository.PreferencesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class DokiApp: Application() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository



    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        // Preloading updated preferences to ensure that the player uses the latest preferences set by the user.
        // This resolves the issue where player use default preferences upon launching the app from a cold start.
        // See [the corresponding issue for more info](https://github.com/anilbeesetti/nextplayer/issues/392)
        applicationScope.launch {
            preferencesRepository.applicationPreferences.first()
            preferencesRepository.playerPreferences.first()
        }
    }
}