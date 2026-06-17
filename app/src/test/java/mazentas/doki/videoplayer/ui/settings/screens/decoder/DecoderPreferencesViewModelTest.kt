package mazentas.doki.videoplayer.ui.settings.screens.decoder

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.DecoderPriority
import mazentas.doki.videoplayer.model.PlayerPreferences
import mazentas.doki.videoplayer.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DecoderPreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: DecoderPreferencesViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = DecoderPreferencesViewModel(repository)
    }

    private val prefs get() = viewModel.preferencesFlow.value

    // region initial state

    @Test
    fun `initial uiState has no dialog`() {
        assertNull(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `initial preferencesFlow has PREFER_DEVICE decoder priority`() {
        assertEquals(DecoderPriority.PREFER_DEVICE, prefs.decoderPriority)
    }

    @Test
    fun `initial preferencesFlow reflects PlayerPreferences defaults`() {
        assertEquals(PlayerPreferences(), prefs)
    }

    // endregion

    // region dialog

    @Test
    fun `showDialog sets DecoderPriorityDialog`() {
        viewModel.showDialog(DecoderPreferenceDialog.DecoderPriorityDialog)
        assertEquals(DecoderPreferenceDialog.DecoderPriorityDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `hideDialog clears dialog`() {
        viewModel.showDialog(DecoderPreferenceDialog.DecoderPriorityDialog)
        viewModel.hideDialog()
        assertNull(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `onEvent ShowDialog with null clears dialog`() {
        viewModel.showDialog(DecoderPreferenceDialog.DecoderPriorityDialog)
        viewModel.onEvent(DecoderPreferencesEvent.ShowDialog(null))
        assertNull(viewModel.uiState.value.showDialog)
    }

    // endregion

    // region updateDecoderPriority

    @Test
    fun `updateDecoderPriority sets PREFER_APP`() {
        viewModel.updateDecoderPriority(DecoderPriority.PREFER_APP)
        assertEquals(DecoderPriority.PREFER_APP, prefs.decoderPriority)
    }

    @Test
    fun `updateDecoderPriority sets DEVICE_ONLY`() {
        viewModel.updateDecoderPriority(DecoderPriority.DEVICE_ONLY)
        assertEquals(DecoderPriority.DEVICE_ONLY, prefs.decoderPriority)
    }

    @Test
    fun `updateDecoderPriority sets back to PREFER_DEVICE`() {
        viewModel.updateDecoderPriority(DecoderPriority.PREFER_APP)
        viewModel.updateDecoderPriority(DecoderPriority.PREFER_DEVICE)
        assertEquals(DecoderPriority.PREFER_DEVICE, prefs.decoderPriority)
    }

    @Test
    fun `updateDecoderPriority does not affect other preferences`() {
        val beforeOtherPrefs = prefs.copy(decoderPriority = DecoderPriority.PREFER_APP)
        viewModel.updateDecoderPriority(DecoderPriority.PREFER_APP)
        assertEquals(beforeOtherPrefs, prefs)
    }

    // endregion
}
