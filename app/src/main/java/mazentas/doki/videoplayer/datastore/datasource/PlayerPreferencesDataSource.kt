package mazentas.doki.videoplayer.datastore.datasource

import androidx.datastore.core.DataStore
import mazentas.doki.videoplayer.model.PlayerPreferences
import javax.inject.Inject
import timber.log.Timber

class PlayerPreferencesDataSource @Inject constructor(
    private val preferencesDataStore: DataStore<PlayerPreferences>,
) : PreferencesDataSource<PlayerPreferences> {

    override val preferences = preferencesDataStore.data

    override suspend fun update(transform: suspend (PlayerPreferences) -> PlayerPreferences) {
        try {
            preferencesDataStore.updateData(transform)
        } catch (ioException: Exception) {
            Timber.tag("NextPlayerPreferences").e("Failed to update app preferences: $ioException")
        }
    }
}
