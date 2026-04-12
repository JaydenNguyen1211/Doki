package mazentas.doki.videoplayer.ui.videopicker

import mazentas.doki.videoplayer.model.Folder


sealed interface MediaState {
    data object Loading : MediaState
    data class Success(val data: Folder?) : MediaState
}