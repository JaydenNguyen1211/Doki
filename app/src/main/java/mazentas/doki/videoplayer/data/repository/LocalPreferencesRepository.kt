package mazentas.doki.videoplayer.data.repository

import mazentas.doki.videoplayer.datastore.datasource.AppPreferencesDataSource
import mazentas.doki.videoplayer.datastore.datasource.PlayerPreferencesDataSource
import mazentas.doki.videoplayer.model.ApplicationPreferences
import mazentas.doki.videoplayer.model.PlayerPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalPreferencesRepository @Inject constructor(
    private val appPreferencesDataSource: AppPreferencesDataSource,
    private val playerPreferencesDataSource: PlayerPreferencesDataSource,
) : PreferencesRepository {
    override val applicationPreferences: Flow<ApplicationPreferences>
        get() = appPreferencesDataSource.preferences

    override val playerPreferences: Flow<PlayerPreferences>
        get() = playerPreferencesDataSource.preferences

    override suspend fun updateApplicationPreferences(
        transform: suspend (ApplicationPreferences) -> ApplicationPreferences,
    ) {
        appPreferencesDataSource.update(transform)
    }

    override suspend fun updatePlayerPreferences(
        transform: suspend (PlayerPreferences) -> PlayerPreferences,
    ) {
        playerPreferencesDataSource.update(transform)
    }
}
