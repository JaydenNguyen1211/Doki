package mazentas.doki.videoplayer.ui.settings.screens.subtitle

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mazentas.doki.videoplayer.model.Font
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.components.CancelButton
import mazentas.doki.videoplayer.ui.components.ClickablePreferenceItem
import mazentas.doki.videoplayer.ui.components.DoneButton
import mazentas.doki.videoplayer.ui.components.ListSectionTitle
import mazentas.doki.videoplayer.ui.components.NextDialog
import mazentas.doki.videoplayer.ui.components.DokiTopAppBar
import mazentas.doki.videoplayer.ui.components.PreferenceSwitch
import mazentas.doki.videoplayer.ui.components.PreferenceSwitchWithDivider
import mazentas.doki.videoplayer.ui.components.RadioTextButton
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons
import mazentas.doki.videoplayer.ui.settings.composables.OptionsDialog
import mazentas.doki.videoplayer.ui.settings.extensions.name
import mazentas.doki.videoplayer.ui.settings.utils.LocalesHelper
import java.nio.charset.Charset

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SubtitlePreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: SubtitlePreferencesViewModel = hiltViewModel(),
) {
    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val languages = remember { listOf(Pair("None", "")) + LocalesHelper.getAvailableLocales() }
    val charsetResource = stringArrayResource(id = R.array.charsets_list)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            DokiTopAppBar(
                title = stringResource(id = R.string.subtitle),
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
            ListSectionTitle(text = stringResource(id = R.string.playback))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = 2
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.preferred_subtitle_lang),
                    description = LocalesHelper.getLocaleDisplayLanguage(preferences.preferredSubtitleLanguage)
                        .takeIf { it.isNotBlank() } ?: stringResource(R.string.preferred_subtitle_lang_description),
                    icon = DokiIcons.Language,
                    onClick = { viewModel.showDialog(SubtitlePreferenceDialog.SubtitleLanguageDialog) },
                    index = 0,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(R.string.subtitle_text_encoding),
                    description = charsetResource.first { it.contains(preferences.subtitleTextEncoding) },
                    icon = DokiIcons.Subtitle,
                    onClick = { viewModel.showDialog(SubtitlePreferenceDialog.SubtitleEncodingDialog) },
                    index = 1,
                    count = totalRows,
                )
            }
            ListSectionTitle(text = stringResource(id = R.string.appearance_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = 6
                PreferenceSwitchWithDivider(
                    title = stringResource(R.string.system_caption_style),
                    description = stringResource(R.string.system_caption_style_desc),
                    icon = DokiIcons.Caption,
                    isChecked = preferences.useSystemCaptionStyle,
                    onChecked = viewModel::toggleUseSystemCaptionStyle,
                    onClick = { context.startActivity(Intent(Settings.ACTION_CAPTIONING_SETTINGS)) },
                    index = 0,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.subtitle_font),
                    description = preferences.subtitleFont.name(),
                    icon = DokiIcons.Font,
                    enabled = preferences.useSystemCaptionStyle.not(),
                    onClick = { viewModel.showDialog(SubtitlePreferenceDialog.SubtitleFontDialog) },
                    index = 1,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.subtitle_text_bold),
                    description = stringResource(id = R.string.subtitle_text_bold_desc),
                    icon = DokiIcons.Bold,
                    enabled = preferences.useSystemCaptionStyle.not(),
                    isChecked = preferences.subtitleTextBold,
                    onClick = viewModel::toggleSubtitleTextBold,
                    index = 2,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.subtitle_text_size),
                    description = preferences.subtitleTextSize.toString(),
                    icon = DokiIcons.FontSize,
                    enabled = preferences.useSystemCaptionStyle.not(),
                    onClick = { viewModel.showDialog(SubtitlePreferenceDialog.SubtitleSizeDialog) },
                    index = 3,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.subtitle_background),
                    description = stringResource(id = R.string.subtitle_background_desc),
                    icon = DokiIcons.Background,
                    enabled = preferences.useSystemCaptionStyle.not(),
                    isChecked = preferences.subtitleBackground,
                    onClick = viewModel::toggleSubtitleBackground,
                    index = 4,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(R.string.embedded_styles),
                    description = stringResource(R.string.embedded_styles_desc),
                    icon = DokiIcons.Style,
                    isChecked = preferences.applyEmbeddedStyles,
                    onClick = viewModel::toggleApplyEmbeddedStyles,
                    index = 5,
                    count = totalRows,
                )
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                SubtitlePreferenceDialog.SubtitleLanguageDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.preferred_subtitle_lang),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(languages) {
                            RadioTextButton(
                                text = it.first,
                                selected = it.second == preferences.preferredSubtitleLanguage,
                                onClick = {
                                    viewModel.updateSubtitleLanguage(it.second)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                SubtitlePreferenceDialog.SubtitleFontDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.subtitle_font),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(Font.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = it == preferences.subtitleFont,
                                onClick = {
                                    viewModel.updateSubtitleFont(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                SubtitlePreferenceDialog.SubtitleSizeDialog -> {
                    var size by remember { mutableIntStateOf(preferences.subtitleTextSize) }

                    NextDialog(
                        onDismissRequest = viewModel::hideDialog,
                        title = { Text(text = stringResource(id = R.string.subtitle_text_size)) },
                        content = {
                            Text(
                                text = size.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                fontSize = size.sp,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = size.toFloat(),
                                onValueChange = { size = it.toInt() },
                                valueRange = 15f..60f,
                            )
                        },
                        confirmButton = {
                            DoneButton(onClick = {
                                viewModel.updateSubtitleFontSize(size)
                                viewModel.hideDialog()
                            })
                        },
                        dismissButton = { CancelButton(onClick = viewModel::hideDialog) },
                    )
                }

                SubtitlePreferenceDialog.SubtitleEncodingDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.subtitle_text_encoding),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(charsetResource) {
                            val currentCharset = it.substringAfterLast("(", "").removeSuffix(")")
                            if (currentCharset.isEmpty() || Charset.isSupported(currentCharset)) {
                                RadioTextButton(
                                    text = it,
                                    selected = currentCharset == preferences.subtitleTextEncoding,
                                    onClick = {
                                        viewModel.updateSubtitleEncoding(currentCharset)
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
}
