package mazentas.doki.videoplayer.ui.settings.screens.medialibrary

import mazentas.doki.videoplayer.data.repository.fake.FakeMediaRepository
import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.ApplicationPreferences
import mazentas.doki.videoplayer.model.Folder
import mazentas.doki.videoplayer.util.MainDispatcherRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MediaLibraryPreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var mediaRepository: FakeMediaRepository
    private lateinit var preferencesRepository: FakePreferencesRepository
    private lateinit var viewModel: MediaLibraryPreferencesViewModel

    @Before
    fun setUp() {
        mediaRepository = FakeMediaRepository()
        preferencesRepository = FakePreferencesRepository()
        viewModel = MediaLibraryPreferencesViewModel(mediaRepository, preferencesRepository)
    }

    // region initial state

    @Test
    fun `initial uiState is Loading`() {
        assertEquals(FolderPreferencesUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `initial preferences reflect ApplicationPreferences defaults`() =
        runTest(mainDispatcherRule.testDispatcher) {
            var latestPrefs = ApplicationPreferences()
            val job = launch { viewModel.preferences.collect { latestPrefs = it } }
            assertEquals(ApplicationPreferences(), latestPrefs)
            job.cancel()
        }

    // endregion

    // region uiState with folders

    @Test
    fun `uiState emits Success with empty folder list when subscribed`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val states = mutableListOf<FolderPreferencesUiState>()
            val job = launch { viewModel.uiState.collect { states.add(it) } }
            assertTrue(states.any { it is FolderPreferencesUiState.Success })
            val success = states.filterIsInstance<FolderPreferencesUiState.Success>().last()
            assertEquals(emptyList<Folder>(), success.directories)
            job.cancel()
        }

    @Test
    fun `uiState emits Success with preloaded folders`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val folder = Folder.sample
            val repoWithFolder = FakeMediaRepository().also { it.directories.add(folder) }
            val vm = MediaLibraryPreferencesViewModel(repoWithFolder, preferencesRepository)

            val states = mutableListOf<FolderPreferencesUiState>()
            val job = launch { vm.uiState.collect { states.add(it) } }
            val success = states.filterIsInstance<FolderPreferencesUiState.Success>().lastOrNull()
            assertTrue(success != null && folder in success.directories)
            job.cancel()
        }

    // endregion

    // region updateExcludeList

    @Test
    fun `updateExcludeList adds path when not excluded`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.updateExcludeList("/storage/path/a")
            assertTrue(prefs.last().excludeFolders.contains("/storage/path/a"))
            job.cancel()
        }

    @Test
    fun `updateExcludeList removes path when already excluded`() =
        runTest(mainDispatcherRule.testDispatcher) {
            preferencesRepository.updateApplicationPreferences {
                it.copy(excludeFolders = listOf("/storage/path/a"))
            }
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.updateExcludeList("/storage/path/a")
            assertFalse(prefs.last().excludeFolders.contains("/storage/path/a"))
            job.cancel()
        }

    @Test
    fun `updateExcludeList can exclude multiple paths`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.updateExcludeList("/storage/path/a")
            viewModel.updateExcludeList("/storage/path/b")
            val excluded = prefs.last().excludeFolders
            assertTrue(excluded.contains("/storage/path/a"))
            assertTrue(excluded.contains("/storage/path/b"))
            job.cancel()
        }

    @Test
    fun `updateExcludeList toggle is idempotent`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.updateExcludeList("/storage/path/a")
            viewModel.updateExcludeList("/storage/path/a")
            assertFalse(prefs.last().excludeFolders.contains("/storage/path/a"))
            job.cancel()
        }

    // endregion

    // region toggleShowFloatingPlayButton

    @Test
    fun `toggleShowFloatingPlayButton flips true to false`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.toggleShowFloatingPlayButton()
            assertEquals(false, prefs.last().showFloatingPlayButton)
            job.cancel()
        }

    @Test
    fun `toggleShowFloatingPlayButton flips false to true`() =
        runTest(mainDispatcherRule.testDispatcher) {
            preferencesRepository.updateApplicationPreferences { it.copy(showFloatingPlayButton = false) }
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.toggleShowFloatingPlayButton()
            assertEquals(true, prefs.last().showFloatingPlayButton)
            job.cancel()
        }

    // endregion

    // region toggleMarkLastPlayedMedia

    @Test
    fun `toggleMarkLastPlayedMedia flips true to false`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.toggleMarkLastPlayedMedia()
            assertEquals(false, prefs.last().markLastPlayedMedia)
            job.cancel()
        }

    @Test
    fun `toggleMarkLastPlayedMedia flips false to true`() =
        runTest(mainDispatcherRule.testDispatcher) {
            preferencesRepository.updateApplicationPreferences { it.copy(markLastPlayedMedia = false) }
            val prefs = mutableListOf<ApplicationPreferences>()
            val job = launch { viewModel.preferences.collect { prefs.add(it) } }
            viewModel.toggleMarkLastPlayedMedia()
            assertEquals(true, prefs.last().markLastPlayedMedia)
            job.cancel()
        }

    // endregion
}
