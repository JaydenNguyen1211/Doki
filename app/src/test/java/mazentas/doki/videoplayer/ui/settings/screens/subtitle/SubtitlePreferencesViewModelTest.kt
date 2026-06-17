package mazentas.doki.videoplayer.ui.settings.screens.subtitle

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.Font
import mazentas.doki.videoplayer.model.PlayerPreferences
import mazentas.doki.videoplayer.util.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubtitlePreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: SubtitlePreferencesViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = SubtitlePreferencesViewModel(repository)
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

    // region dialogs

    @Test
    fun `showDialog sets SubtitleLanguageDialog`() {
        viewModel.showDialog(SubtitlePreferenceDialog.SubtitleLanguageDialog)
        assertEquals(SubtitlePreferenceDialog.SubtitleLanguageDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets SubtitleFontDialog`() {
        viewModel.showDialog(SubtitlePreferenceDialog.SubtitleFontDialog)
        assertEquals(SubtitlePreferenceDialog.SubtitleFontDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets SubtitleSizeDialog`() {
        viewModel.showDialog(SubtitlePreferenceDialog.SubtitleSizeDialog)
        assertEquals(SubtitlePreferenceDialog.SubtitleSizeDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets SubtitleEncodingDialog`() {
        viewModel.showDialog(SubtitlePreferenceDialog.SubtitleEncodingDialog)
        assertEquals(SubtitlePreferenceDialog.SubtitleEncodingDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `hideDialog clears dialog`() {
        viewModel.showDialog(SubtitlePreferenceDialog.SubtitleLanguageDialog)
        viewModel.hideDialog()
        assertNull(viewModel.uiState.value.showDialog)
    }

    // endregion

    // region updateSubtitleLanguage

    @Test
    fun `updateSubtitleLanguage sets language`() {
        viewModel.updateSubtitleLanguage("vi")
        assertEquals("vi", prefs.preferredSubtitleLanguage)
    }

    @Test
    fun `updateSubtitleLanguage overwrites previous language`() {
        viewModel.updateSubtitleLanguage("en")
        viewModel.updateSubtitleLanguage("fr")
        assertEquals("fr", prefs.preferredSubtitleLanguage)
    }

    // endregion

    // region updateSubtitleFont

    @Test
    fun `updateSubtitleFont sets MONOSPACE`() {
        viewModel.updateSubtitleFont(Font.MONOSPACE)
        assertEquals(Font.MONOSPACE, prefs.subtitleFont)
    }

    @Test
    fun `updateSubtitleFont sets SERIF`() {
        viewModel.updateSubtitleFont(Font.SERIF)
        assertEquals(Font.SERIF, prefs.subtitleFont)
    }

    @Test
    fun `updateSubtitleFont sets back to DEFAULT`() {
        viewModel.updateSubtitleFont(Font.MONOSPACE)
        viewModel.updateSubtitleFont(Font.DEFAULT)
        assertEquals(Font.DEFAULT, prefs.subtitleFont)
    }

    // endregion

    // region updateSubtitleFontSize

    @Test
    fun `updateSubtitleFontSize sets value`() {
        viewModel.updateSubtitleFontSize(24)
        assertEquals(24, prefs.subtitleTextSize)
    }

    @Test
    fun `updateSubtitleFontSize overwrites previous value`() {
        viewModel.updateSubtitleFontSize(16)
        viewModel.updateSubtitleFontSize(32)
        assertEquals(32, prefs.subtitleTextSize)
    }

    // endregion

    // region updateSubtitleEncoding

    @Test
    fun `updateSubtitleEncoding sets encoding`() {
        viewModel.updateSubtitleEncoding("UTF-8")
        assertEquals("UTF-8", prefs.subtitleTextEncoding)
    }

    // endregion

    // region boolean toggles

    @Test
    fun `toggleSubtitleTextBold flips true to false`() {
        // default subtitleTextBold = true
        viewModel.toggleSubtitleTextBold()
        assertEquals(false, prefs.subtitleTextBold)
    }

    @Test
    fun `toggleSubtitleTextBold flips false to true`() {
        viewModel.toggleSubtitleTextBold()
        viewModel.toggleSubtitleTextBold()
        assertEquals(true, prefs.subtitleTextBold)
    }

    @Test
    fun `toggleSubtitleBackground flips false to true`() {
        // default subtitleBackground = false
        viewModel.toggleSubtitleBackground()
        assertEquals(true, prefs.subtitleBackground)
    }

    @Test
    fun `toggleApplyEmbeddedStyles flips true to false`() {
        // default applyEmbeddedStyles = true
        viewModel.toggleApplyEmbeddedStyles()
        assertEquals(false, prefs.applyEmbeddedStyles)
    }

    @Test
    fun `toggleUseSystemCaptionStyle flips false to true`() {
        // default useSystemCaptionStyle = false
        viewModel.toggleUseSystemCaptionStyle()
        assertEquals(true, prefs.useSystemCaptionStyle)
    }

    // endregion
}
