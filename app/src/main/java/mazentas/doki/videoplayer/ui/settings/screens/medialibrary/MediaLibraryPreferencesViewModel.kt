package mazentas.doki.videoplayer.ui.settings.screens.medialibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import mazentas.doki.videoplayer.data.repository.MediaRepository
import mazentas.doki.videoplayer.data.repository.PreferencesRepository
import mazentas.doki.videoplayer.model.ApplicationPreferences
import mazentas.doki.videoplayer.model.Folder
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MediaLibraryPreferencesViewModel @Inject constructor(
    mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    val uiState = mediaRepository.getFoldersFlow()
        .map { FolderPreferencesUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FolderPreferencesUiState.Loading,
        )

    val preferences = preferencesRepository.applicationPreferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ApplicationPreferences(),
        )

    fun updateExcludeList(path: String) {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(
                    excludeFolders = if (path in it.excludeFolders) {
                        it.excludeFolders - path
                    } else {
                        it.excludeFolders + path
                    },
                )
            }
        }
    }

    fun toggleShowFloatingPlayButton() {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(showFloatingPlayButton = !it.showFloatingPlayButton)
            }
        }
    }

    fun toggleMarkLastPlayedMedia() {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(markLastPlayedMedia = !it.markLastPlayedMedia)
            }
        }
    }
}

sealed interface FolderPreferencesUiState {
    object Loading : FolderPreferencesUiState

    data class Success(val directories: List<Folder>) : FolderPreferencesUiState
}
