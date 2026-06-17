package mazentas.doki.videoplayer.ui.settings.screens.appearance

import mazentas.doki.videoplayer.data.repository.fake.FakePreferencesRepository
import mazentas.doki.videoplayer.model.ThemeConfig
import mazentas.doki.videoplayer.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppearancePreferencesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePreferencesRepository
    private lateinit var viewModel: AppearancePreferencesViewModel

    @Before
    fun setUp() {
        repository = FakePreferencesRepository()
        viewModel = AppearancePreferencesViewModel(repository)
    }

    // region initial state

    @Test
    fun `initial uiState has no dialog`() {
        assertNull(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `initial preferencesFlow reflects defaults`() {
        assertEquals(ThemeConfig.SYSTEM, viewModel.preferencesFlow.value.themeConfig)
        assertEquals(true, viewModel.preferencesFlow.value.useDynamicColors)
        assertEquals(false, viewModel.preferencesFlow.value.useHighContrastDarkTheme)
    }

    // endregion

    // region dialog

    @Test
    fun `showDialog sets dialog in uiState`() {
        viewModel.showDialog(AppearancePreferenceDialog.Theme)
        assertEquals(AppearancePreferenceDialog.Theme, viewModel.uiState.value.showDialog)
    }

    @Test
    fun `hideDialog clears dialog from uiState`() {
        viewModel.showDialog(AppearancePreferenceDialog.Theme)
        viewModel.hideDialog()
        assertNull(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `onEvent ShowDialog with null clears dialog`() {
        viewModel.showDialog(AppearancePreferenceDialog.Theme)
        viewModel.onEvent(AppearancePreferencesEvent.ShowDialog(null))
        assertNull(viewModel.uiState.value.showDialog)
    }

    // endregion

    // region toggleDarkTheme

    @Test
    fun `toggleDarkTheme changes SYSTEM theme to ON`() {
        viewModel.toggleDarkTheme()
        assertEquals(ThemeConfig.ON, viewModel.preferencesFlow.value.themeConfig)
    }

    @Test
    fun `toggleDarkTheme changes ON theme to OFF`() = runTest {
        repository.updateApplicationPreferences { it.copy(themeConfig = ThemeConfig.ON) }
        viewModel.toggleDarkTheme()
        assertEquals(ThemeConfig.OFF, viewModel.preferencesFlow.value.themeConfig)
    }

    @Test
    fun `toggleDarkTheme changes OFF theme to ON`() = runTest {
        repository.updateApplicationPreferences { it.copy(themeConfig = ThemeConfig.OFF) }
        viewModel.toggleDarkTheme()
        assertEquals(ThemeConfig.ON, viewModel.preferencesFlow.value.themeConfig)
    }

    // endregion

    // region updateThemeConfig

    @Test
    fun `updateThemeConfig sets OFF`() {
        viewModel.updateThemeConfig(ThemeConfig.OFF)
        assertEquals(ThemeConfig.OFF, viewModel.preferencesFlow.value.themeConfig)
    }

    @Test
    fun `updateThemeConfig sets ON`() {
        viewModel.updateThemeConfig(ThemeConfig.ON)
        assertEquals(ThemeConfig.ON, viewModel.preferencesFlow.value.themeConfig)
    }

    @Test
    fun `updateThemeConfig sets SYSTEM`() = runTest {
        repository.updateApplicationPreferences { it.copy(themeConfig = ThemeConfig.ON) }
        viewModel.updateThemeConfig(ThemeConfig.SYSTEM)
        assertEquals(ThemeConfig.SYSTEM, viewModel.preferencesFlow.value.themeConfig)
    }

    // endregion

    // region toggleUseDynamicColors

    @Test
    fun `toggleUseDynamicColors flips true to false`() {
        // default useDynamicColors = true
        viewModel.toggleUseDynamicColors()
        assertEquals(false, viewModel.preferencesFlow.value.useDynamicColors)
    }

    @Test
    fun `toggleUseDynamicColors flips false to true`() = runTest {
        repository.updateApplicationPreferences { it.copy(useDynamicColors = false) }
        viewModel.toggleUseDynamicColors()
        assertEquals(true, viewModel.preferencesFlow.value.useDynamicColors)
    }

    // endregion

    // region toggleUseHighContrastDarkTheme

    @Test
    fun `toggleUseHighContrastDarkTheme flips false to true`() {
        // default useHighContrastDarkTheme = false
        viewModel.toggleUseHighContrastDarkTheme()
        assertEquals(true, viewModel.preferencesFlow.value.useHighContrastDarkTheme)
    }

    @Test
    fun `toggleUseHighContrastDarkTheme flips true to false`() = runTest {
        repository.updateApplicationPreferences { it.copy(useHighContrastDarkTheme = true) }
        viewModel.toggleUseHighContrastDarkTheme()
        assertEquals(false, viewModel.preferencesFlow.value.useHighContrastDarkTheme)
    }

    // endregion
}
