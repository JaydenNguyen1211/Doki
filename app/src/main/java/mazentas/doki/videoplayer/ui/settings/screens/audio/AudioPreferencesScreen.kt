package mazentas.doki.videoplayer.ui.settings.screens.audio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.components.ClickablePreferenceItem
import mazentas.doki.videoplayer.ui.components.ListSectionTitle
import mazentas.doki.videoplayer.ui.components.NextTopAppBar
import mazentas.doki.videoplayer.ui.components.PreferenceSwitch
import mazentas.doki.videoplayer.ui.components.RadioTextButton
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons
import mazentas.doki.videoplayer.ui.settings.composables.OptionsDialog
import mazentas.doki.videoplayer.ui.settings.utils.LocalesHelper

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AudioPreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: AudioPreferencesViewModel = hiltViewModel(),
) {
    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val languages = remember { listOf(Pair("None", "")) + LocalesHelper.getAvailableLocales() }

    Scaffold(
        topBar = {
            NextTopAppBar(
                title = stringResource(id = R.string.audio),
                navigationIcon = {
                    FilledTonalIconButton(onClick = onNavigateUp) {
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
            ListSectionTitle(text = stringResource(id = R.string.playback))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = 4
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.preferred_audio_lang),
                    description = LocalesHelper.getLocaleDisplayLanguage(preferences.preferredAudioLanguage)
                        .takeIf { it.isNotBlank() } ?: stringResource(R.string.preferred_audio_lang_description),
                    icon = DokiIcons.Language,
                    onClick = { viewModel.showDialog(AudioPreferenceDialog.AudioLanguageDialog) },
                    index = 0,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(R.string.require_audio_focus),
                    description = stringResource(R.string.require_audio_focus_desc),
                    icon = DokiIcons.Focus,
                    isChecked = preferences.requireAudioFocus,
                    onClick = viewModel::toggleRequireAudioFocus,
                    index = 1,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.pause_on_headset_disconnect),
                    description = stringResource(id = R.string.pause_on_headset_disconnect_desc),
                    icon = DokiIcons.HeadsetOff,
                    isChecked = preferences.pauseOnHeadsetDisconnect,
                    onClick = viewModel::togglePauseOnHeadsetDisconnect,
                    index = 2,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.system_volume_panel),
                    description = stringResource(id = R.string.system_volume_panel_desc),
                    icon = DokiIcons.Headset,
                    isChecked = preferences.showSystemVolumePanel,
                    onClick = viewModel::toggleShowSystemVolumePanel,
                    index = 3,
                    count = totalRows,
                )
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                AudioPreferenceDialog.AudioLanguageDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.preferred_audio_lang),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(languages) {
                            RadioTextButton(
                                text = it.first,
                                selected = it.second == preferences.preferredAudioLanguage,
                                onClick = {
                                    viewModel.updateAudioLanguage(it.second)
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
