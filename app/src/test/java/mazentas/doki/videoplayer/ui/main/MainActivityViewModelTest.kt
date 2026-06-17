package mazentas.doki.videoplayer.ui.main

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.ApplicationPreferences
import mazentas.doki.videoplayer.util.MainDispatcherRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = MainActivityViewModel(repository)
    }

    @Test
    fun `initial uiState is Loading before any subscriber`() {
        assertEquals(MainActivityUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState becomes Success with default preferences when subscribed`() =
        runTest(mainDispatcherRule.testDispatcher) {
            var latest: MainActivityUiState = MainActivityUiState.Loading
            val job = launch { viewModel.uiState.collect { latest = it } }
            assertTrue(latest is MainActivityUiState.Success)
            assertEquals(ApplicationPreferences(), (latest as MainActivityUiState.Success).preferences)
            job.cancel()
        }

    @Test
    fun `uiState reflects updated preferences`() = runTest(mainDispatcherRule.testDispatcher) {
        val states = mutableListOf<MainActivityUiState>()
        val job = launch { viewModel.uiState.collect { states.add(it) } }
        repository.updateApplicationPreferences { it.copy(useDynamicColors = false) }
        val last = states.last() as MainActivityUiState.Success
        assertEquals(false, last.preferences.useDynamicColors)
        job.cancel()
    }
}
