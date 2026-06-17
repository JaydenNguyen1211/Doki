package mazentas.doki.videoplayer.ui.settings.screens.audio

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.PlayerPreferences
import mazentas.doki.videoplayer.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AudioPreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: AudioPreferencesViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = AudioPreferencesViewModel(repository)
    }

    private val prefs get() = viewModel.preferencesFlow.value

    // region initial state

    @Test
    fun `initial uiState has no dialog`() {
        assertNull(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `initial preferencesFlow reflects PlayerPreferences defaults`() {
        assertEquals(PlayerPreferences(), prefs)
    }

    // endregion

    // region dialog

    @Test
    fun `showDialog sets AudioLanguageDialog`() {
        viewModel.showDialog(AudioPreferenceDialog.AudioLanguageDialog)
        assertEquals(AudioPreferenceDialog.AudioLanguageDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `hideDialog clears dialog`() {
        viewModel.showDialog(AudioPreferenceDialog.AudioLanguageDialog)
        viewModel.hideDialog()
        assertNull(viewModel.uiState.value.showDialog)
    }

    // endregion

    // region updateAudioLanguage

    @Test
    fun `updateAudioLanguage sets language code`() {
        viewModel.updateAudioLanguage("en")
        assertEquals("en", prefs.preferredAudioLanguage)
    }

    @Test
    fun `updateAudioLanguage overwrites previous language`() {
        viewModel.updateAudioLanguage("en")
        viewModel.updateAudioLanguage("vi")
        assertEquals("vi", prefs.preferredAudioLanguage)
    }

    @Test
    fun `updateAudioLanguage accepts empty string`() {
        viewModel.updateAudioLanguage("en")
        viewModel.updateAudioLanguage("")
        assertEquals("", prefs.preferredAudioLanguage)
    }

    // endregion

    // region togglePauseOnHeadsetDisconnect

    @Test
    fun `togglePauseOnHeadsetDisconnect flips true to false`() {
        // default pauseOnHeadsetDisconnect = true
        viewModel.togglePauseOnHeadsetDisconnect()
        assertEquals(false, prefs.pauseOnHeadsetDisconnect)
    }

    @Test
    fun `togglePauseOnHeadsetDisconnect flips false to true`() {
        viewModel.togglePauseOnHeadsetDisconnect()
        viewModel.togglePauseOnHeadsetDisconnect()
        assertEquals(true, prefs.pauseOnHeadsetDisconnect)
    }

    // endregion

    // region toggleShowSystemVolumePanel

    @Test
    fun `toggleShowSystemVolumePanel flips true to false`() {
        // default showSystemVolumePanel = true
        viewModel.toggleShowSystemVolumePanel()
        assertEquals(false, prefs.showSystemVolumePanel)
    }

    @Test
    fun `toggleShowSystemVolumePanel flips false to true`() {
        viewModel.toggleShowSystemVolumePanel()
        viewModel.toggleShowSystemVolumePanel()
        assertEquals(true, prefs.showSystemVolumePanel)
    }

    // endregion

    // region toggleRequireAudioFocus

    @Test
    fun `toggleRequireAudioFocus flips true to false`() {
        // default requireAudioFocus = true
        viewModel.toggleRequireAudioFocus()
        assertEquals(false, prefs.requireAudioFocus)
    }

    @Test
    fun `toggleRequireAudioFocus flips false to true`() {
        viewModel.toggleRequireAudioFocus()
        viewModel.toggleRequireAudioFocus()
        assertEquals(true, prefs.requireAudioFocus)
    }

    // endregion
}
