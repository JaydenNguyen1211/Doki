package mazentas.doki.videoplayer.ui.settings.screens.medialibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaLibraryPreferencesScreen(
    onNavigateUp: () -> Unit,
    onFolderSettingClick: () -> Unit = {},
    viewModel: MediaLibraryPreferencesViewModel = hiltViewModel(),
) {
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NextTopAppBar(
                title = stringResource(id = R.string.media_library),
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
            ListSectionTitle(text = stringResource(id = R.string.appearance_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                PreferenceSwitch(
                    title = stringResource(id = R.string.mark_last_played_media),
                    description = stringResource(
                        id = R.string.mark_last_played_media_desc,
                    ),
                    icon = DokiIcons.Check,
                    isChecked = preferences.markLastPlayedMedia,
                    onClick = viewModel::toggleMarkLastPlayedMedia,
                    index = 0,
                    count = 2,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.floating_play_button),
                    description = stringResource(
                        id = R.string.floating_play_button_desc,
                    ),
                    icon = DokiIcons.SmartButton,
                    isChecked = preferences.showFloatingPlayButton,
                    onClick = viewModel::toggleShowFloatingPlayButton,
                    index = 1,
                    count = 2,
                )
            }

            ListSectionTitle(text = stringResource(id = R.string.scan))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.manage_folders),
                    description = stringResource(id = R.string.manage_folders_desc),
                    icon = DokiIcons.FolderOff,
                    onClick = onFolderSettingClick,
                )
            }
        }
    }
}

@Composable
fun HideFoldersSettings(
    onClick: () -> Unit,
) {
    ClickablePreferenceItem(
        title = stringResource(id = R.string.manage_folders),
        description = stringResource(id = R.string.manage_folders_desc),
        icon = DokiIcons.FolderOff,
        onClick = onClick,
    )
}

@Composable
fun MarkLastPlayedMediaSetting(
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    PreferenceSwitch(
        title = stringResource(id = R.string.mark_last_played_media),
        description = stringResource(
            id = R.string.mark_last_played_media_desc,
        ),
        icon = DokiIcons.Check,
        isChecked = isChecked,
        onClick = onClick,
    )
}

@Composable
fun FloatingPlayButtonSetting(
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    PreferenceSwitch(
        title = stringResource(id = R.string.floating_play_button),
        description = stringResource(
            id = R.string.floating_play_button_desc,
        ),
        icon = DokiIcons.SmartButton,
        isChecked = isChecked,
        onClick = onClick,
    )
}
