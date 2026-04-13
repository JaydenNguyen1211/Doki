package mazentas.doki.videoplayer.ui.settings

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.components.ClickablePreferenceItem
import mazentas.doki.videoplayer.ui.components.NextTopAppBar
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onItemClick: (Setting) -> Unit,
) {
    Scaffold(
        topBar = {
            NextTopAppBar(
                title = stringResource(id = R.string.settings),
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
        val settingRows = remember { SettingRow.entries }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
        ) {
            settingRows.forEachIndexed { index, row ->
                ClickablePreferenceItem(
                    title = stringResource(id = row.titleResId),
                    description = stringResource(id = row.descriptionResId),
                    icon = row.icon,
                    onClick = { onItemClick(row.setting) },
                    index = index,
                    count = settingRows.size,
                )
            }
        }
    }
}

enum class Setting {
    APPEARANCE,
    MEDIA_LIBRARY,
    PLAYER,
    DECODER,
    AUDIO,
    SUBTITLE,
    ABOUT,
}

private enum class SettingRow(
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: ImageVector,
    val setting: Setting,
) {
    APPEARANCE(
        titleResId = R.string.appearance_name,
        descriptionResId = R.string.appearance_description,
        icon = DokiIcons.Appearance,
        setting = Setting.APPEARANCE,
    ),
    MEDIA_LIBRARY(
        titleResId = R.string.media_library,
        descriptionResId = R.string.media_library_description,
        icon = DokiIcons.Movie,
        setting = Setting.MEDIA_LIBRARY,
    ),
    PLAYER(
        titleResId = R.string.player_name,
        descriptionResId = R.string.player_description,
        icon = DokiIcons.Player,
        setting = Setting.PLAYER,
    ),
    DECODER(
        titleResId = R.string.decoder,
        descriptionResId = R.string.decoder_desc,
        icon = DokiIcons.Decoder,
        setting = Setting.DECODER,
    ),
    AUDIO(
        titleResId = R.string.audio,
        descriptionResId = R.string.audio_desc,
        icon = DokiIcons.Audio,
        setting = Setting.AUDIO,
    ),
    SUBTITLE(
        titleResId = R.string.subtitle,
        descriptionResId = R.string.subtitle_desc,
        icon = DokiIcons.Subtitle,
        setting = Setting.SUBTITLE,
    ),
    ABOUT(
        titleResId = R.string.about_name,
        descriptionResId = R.string.about_description,
        icon = DokiIcons.Info,
        setting = Setting.ABOUT,
    ),
}
