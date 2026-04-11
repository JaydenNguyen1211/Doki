package mazentas.doki.videoplayer.domain

import mazentas.doki.videoplayer.data.repository.MediaRepository
import mazentas.doki.videoplayer.data.repository.PreferencesRepository
import mazentas.doki.videoplayer.model.Sort
import mazentas.doki.videoplayer.model.Video
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import mazentas.doki.videoplayer.common.Dispatcher
import mazentas.doki.videoplayer.common.DokiDispatcher
import javax.inject.Inject

class GetSortedVideosUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @Dispatcher(DokiDispatcher.Default) private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    operator fun invoke(folderPath: String? = null): Flow<List<Video>> {
        val videosFlow = if (folderPath != null) {
            mediaRepository.getVideosFlowFromFolderPath(folderPath)
        } else {
            mediaRepository.getVideosFlow()
        }

        return combine(
            videosFlow,
            preferencesRepository.applicationPreferences,
        ) { videoItems, preferences ->

            val nonExcludedVideos = videoItems.filterNot {
                it.parentPath in preferences.excludeFolders
            }

            val sort = Sort(by = preferences.sortBy, order = preferences.sortOrder)
            nonExcludedVideos.sortedWith(sort.videoComparator())
        }.flowOn(defaultDispatcher)
    }
}
