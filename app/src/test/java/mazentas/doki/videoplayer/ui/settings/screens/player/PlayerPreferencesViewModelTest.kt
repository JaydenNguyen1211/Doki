package mazentas.doki.videoplayer.ui.settings.screens.player

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.ControlButtonsPosition
import mazentas.doki.videoplayer.model.DoubleTapGesture
import mazentas.doki.videoplayer.model.PlayerPreferences
import mazentas.doki.videoplayer.model.Resume
import mazentas.doki.videoplayer.model.ScreenOrientation
import mazentas.doki.videoplayer.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlayerPreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: PlayerPreferencesViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = PlayerPreferencesViewModel(repository)
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
    fun `showDialog sets ResumeDialog`() {
        viewModel.showDialog(PlayerPreferenceDialog.ResumeDialog)
        assertEquals(PlayerPreferenceDialog.ResumeDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets DoubleTapDialog`() {
        viewModel.showDialog(PlayerPreferenceDialog.DoubleTapDialog)
        assertEquals(PlayerPreferenceDialog.DoubleTapDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets PlaybackSpeedDialog`() {
        viewModel.showDialog(PlayerPreferenceDialog.PlaybackSpeedDialog)
        assertEquals(PlayerPreferenceDialog.PlaybackSpeedDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `showDialog sets SeekIncrementDialog`() {
        viewModel.showDialog(PlayerPreferenceDialog.SeekIncrementDialog)
        assertEquals(PlayerPreferenceDialog.SeekIncrementDialog, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `hideDialog clears dialog`() {
        viewModel.showDialog(PlayerPreferenceDialog.ResumeDialog)
        viewModel.hideDialog()
        assertNull(viewModel.uiState.value.showDialog)
    }

    // endregion

    // region updatePlaybackResume

    @Test
    fun `updatePlaybackResume sets NO`() {
        viewModel.updatePlaybackResume(Resume.NO)
        assertEquals(Resume.NO, prefs.resume)
    }

    @Test
    fun `updatePlaybackResume sets YES`() = runTest {
        repository.updatePlayerPreferences { it.copy(resume = Resume.NO) }
        viewModel.updatePlaybackResume(Resume.YES)
        assertEquals(Resume.YES, prefs.resume)
    }

    // endregion

    // region updateDoubleTapGesture

    @Test
    fun `updateDoubleTapGesture sets PLAY_PAUSE`() {
        viewModel.updateDoubleTapGesture(DoubleTapGesture.PLAY_PAUSE)
        assertEquals(DoubleTapGesture.PLAY_PAUSE, prefs.doubleTapGesture)
    }

    @Test
    fun `updateDoubleTapGesture sets NONE`() {
        viewModel.updateDoubleTapGesture(DoubleTapGesture.NONE)
        assertEquals(DoubleTapGesture.NONE, prefs.doubleTapGesture)
    }

    // endregion

    // region toggleDoubleTapGesture

    @Test
    fun `toggleDoubleTapGesture changes BOTH to NONE`() {
        // default doubleTapGesture = BOTH
        viewModel.toggleDoubleTapGesture()
        assertEquals(DoubleTapGesture.NONE, prefs.doubleTapGesture)
    }

    @Test
    fun `toggleDoubleTapGesture changes NONE to FAST_FORWARD_AND_REWIND`() = runTest {
        repository.updatePlayerPreferences { it.copy(doubleTapGesture = DoubleTapGesture.NONE) }
        viewModel.toggleDoubleTapGesture()
        assertEquals(DoubleTapGesture.FAST_FORWARD_AND_REWIND, prefs.doubleTapGesture)
    }

    // endregion

    // region boolean toggles

    @Test
    fun `toggleAutoplay flips true to false`() {
        // default autoplay = true
        viewModel.toggleAutoplay()
        assertEquals(false, prefs.autoplay)
    }

    @Test
    fun `toggleAutoPip flips true to false`() {
        // default autoPip = true
        viewModel.toggleAutoPip()
        assertEquals(false, prefs.autoPip)
    }

    @Test
    fun `toggleAutoBackgroundPlay flips false to true`() {
        // default autoBackgroundPlay = false
        viewModel.toggleAutoBackgroundPlay()
        assertEquals(true, prefs.autoBackgroundPlay)
    }

    @Test
    fun `toggleRememberBrightnessLevel flips false to true`() {
        // default rememberPlayerBrightness = false
        viewModel.toggleRememberBrightnessLevel()
        assertEquals(true, prefs.rememberPlayerBrightness)
    }

    @Test
    fun `toggleUseSwipeControls flips true to false`() {
        // default useSwipeControls = true
        viewModel.toggleUseSwipeControls()
        assertEquals(false, prefs.useSwipeControls)
    }

    @Test
    fun `toggleUseSeekControls flips true to false`() {
        // default useSeekControls = true
        viewModel.toggleUseSeekControls()
        assertEquals(false, prefs.useSeekControls)
    }

    @Test
    fun `toggleUseZoomControls flips true to false`() {
        // default useZoomControls = true
        viewModel.toggleUseZoomControls()
        assertEquals(false, prefs.useZoomControls)
    }

    @Test
    fun `toggleRememberSelections flips true to false`() {
        // default rememberSelections = true
        viewModel.toggleRememberSelections()
        assertEquals(false, prefs.rememberSelections)
    }

    @Test
    fun `toggleUseLongPressControls flips false to true`() {
        // default useLongPressControls = false
        viewModel.toggleUseLongPressControls()
        assertEquals(true, prefs.useLongPressControls)
    }

    // endregion

    // region orientation and position

    @Test
    fun `updatePreferredPlayerOrientation sets LANDSCAPE`() {
        viewModel.updatePreferredPlayerOrientation(ScreenOrientation.LANDSCAPE)
        assertEquals(ScreenOrientation.LANDSCAPE, prefs.playerScreenOrientation)
    }

    @Test
    fun `updatePreferredPlayerOrientation sets PORTRAIT`() {
        viewModel.updatePreferredPlayerOrientation(ScreenOrientation.PORTRAIT)
        assertEquals(ScreenOrientation.PORTRAIT, prefs.playerScreenOrientation)
    }

    @Test
    fun `updatePreferredControlButtonsPosition sets RIGHT`() {
        viewModel.updatePreferredControlButtonsPosition(ControlButtonsPosition.RIGHT)
        assertEquals(ControlButtonsPosition.RIGHT, prefs.controlButtonsPosition)
    }

    // endregion

    // region numeric preferences

    @Test
    fun `updateDefaultPlaybackSpeed sets value`() {
        viewModel.updateDefaultPlaybackSpeed(1.5f)
        assertEquals(1.5f, prefs.defaultPlaybackSpeed)
    }

    @Test
    fun `updateLongPressControlsSpeed sets value`() {
        viewModel.updateLongPressControlsSpeed(3.0f)
        assertEquals(3.0f, prefs.longPressControlsSpeed)
    }

    @Test
    fun `updateControlAutoHideTimeout sets value`() {
        viewModel.updateControlAutoHideTimeout(5)
        assertEquals(5, prefs.controllerAutoHideTimeout)
    }

    @Test
    fun `updateSeekIncrement sets value`() {
        viewModel.updateSeekIncrement(10)
        assertEquals(10, prefs.seekIncrement)
    }

    // endregion
}
