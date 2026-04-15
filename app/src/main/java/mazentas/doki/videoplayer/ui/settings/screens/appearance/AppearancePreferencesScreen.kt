package mazentas.doki.videoplayer.ui.settings.screens.appearance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import mazentas.doki.videoplayer.ui.designsystem.DokiIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mazentas.doki.videoplayer.model.ThemeConfig
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.components.ListSectionTitle
import mazentas.doki.videoplayer.ui.components.DokiTopAppBar
import mazentas.doki.videoplayer.ui.components.PreferenceSwitch
import mazentas.doki.videoplayer.ui.components.PreferenceSwitchWithDivider
import mazentas.doki.videoplayer.ui.components.RadioTextButton
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons
import mazentas.doki.videoplayer.ui.settings.composables.OptionsDialog
import mazentas.doki.videoplayer.ui.settings.extensions.name
import mazentas.doki.videoplayer.ui.theme.supportsDynamicTheming

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppearancePreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: AppearancePreferencesViewModel = hiltViewModel(),
) {
    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            // TODO: Check why the appbar flickers when changing the theme with small appbar and not with large appbar
            DokiTopAppBar(
                title = stringResource(id = R.string.appearance_name),
                navigationIcon = {
                    DokiIconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = DokiIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            ListSectionTitle(text = stringResource(id = R.string.appearance_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = if (supportsDynamicTheming()) 3 else 2
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.dark_theme),
                    description = preferences.themeConfig.name(),
                    isChecked = preferences.themeConfig == ThemeConfig.ON,
                    onChecked = viewModel::toggleDarkTheme,
                    icon = DokiIcons.DarkMode,
                    onClick = { viewModel.showDialog(AppearancePreferenceDialog.Theme) },
                    index = 0,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(R.string.high_contrast_dark_theme),
                    description = stringResource(R.string.high_contrast_dark_theme_desc),
                    icon = DokiIcons.Contrast,
                    isChecked = preferences.useHighContrastDarkTheme,
                    onClick = viewModel::toggleUseHighContrastDarkTheme,
                    index = 1,
                    count = totalRows,
                )
                if (supportsDynamicTheming()) {
                    PreferenceSwitch(
                        title = stringResource(id = R.string.dynamic_theme),
                        description = stringResource(id = R.string.dynamic_theme_description),
                        icon = DokiIcons.Appearance,
                        isChecked = preferences.useDynamicColors,
                        onClick = viewModel::toggleUseDynamicColors,
                        index = 2,
                        count = totalRows,
                    )
                }
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                AppearancePreferenceDialog.Theme -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.dark_theme),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(ThemeConfig.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = (it == preferences.themeConfig),
                                onClick = {
                                    viewModel.updateThemeConfig(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
