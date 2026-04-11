package mazentas.doki.videoplayer.domain

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import mazentas.doki.videoplayer.common.extensions.getPath
import mazentas.doki.videoplayer.data.repository.PreferencesRepository
import mazentas.doki.videoplayer.model.MediaViewMode
import mazentas.doki.videoplayer.model.Video
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import mazentas.doki.videoplayer.common.Dispatcher
import mazentas.doki.videoplayer.common.DokiDispatcher
import java.io.File
import javax.inject.Inject

class GetSortedPlaylistUseCase @Inject constructor(
    private val getSortedVideosUseCase: GetSortedVideosUseCase,
    private val preferencesRepository: PreferencesRepository,
    @ApplicationContext private val context: Context,
    @Dispatcher(DokiDispatcher.Default) private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(uri: Uri): List<Video> = withContext(defaultDispatcher) {
        val path = context.getPath(uri) ?: return@withContext emptyList()
        val parent = File(path).parent.takeIf {
            preferencesRepository.applicationPreferences.first().mediaViewMode != MediaViewMode.VIDEOS
        }

        getSortedVideosUseCase.invoke(parent).first()
    }
}
