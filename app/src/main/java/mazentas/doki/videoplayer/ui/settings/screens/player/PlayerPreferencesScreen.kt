package mazentas.doki.videoplayer.ui.settings.screens.player

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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mazentas.doki.videoplayer.common.extensions.round
import mazentas.doki.videoplayer.model.ControlButtonsPosition
import mazentas.doki.videoplayer.model.DoubleTapGesture
import mazentas.doki.videoplayer.model.Resume
import mazentas.doki.videoplayer.model.ScreenOrientation
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.components.ClickablePreferenceItem
import mazentas.doki.videoplayer.ui.components.ListSectionTitle
import mazentas.doki.videoplayer.ui.components.NextDialogWithDoneAndCancelButtons
import mazentas.doki.videoplayer.ui.components.DokiTopAppBar
import mazentas.doki.videoplayer.ui.components.PreferenceSwitch
import mazentas.doki.videoplayer.ui.components.PreferenceSwitchWithDivider
import mazentas.doki.videoplayer.ui.components.RadioTextButton
import mazentas.doki.videoplayer.ui.designsystem.DokiIcons
import mazentas.doki.videoplayer.ui.settings.composables.OptionsDialog
import mazentas.doki.videoplayer.ui.settings.extensions.name

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerPreferencesScreen(
    onNavigateUp: () -> Unit,
    viewModel: PlayerPreferencesViewModel = hiltViewModel(),
) {
    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DokiTopAppBar(
                title = stringResource(id = R.string.player_name),
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
            ListSectionTitle(text = stringResource(id = R.string.interface_name))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = 8
                PreferenceSwitch(
                    title = stringResource(id = R.string.seek_gesture),
                    description = stringResource(id = R.string.seek_gesture_description),
                    icon = DokiIcons.SwipeHorizontal,
                    isChecked = preferences.useSeekControls,
                    onClick = viewModel::toggleUseSeekControls,
                    index = 0,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.swipe_gesture),
                    description = stringResource(id = R.string.swipe_gesture_description),
                    icon = DokiIcons.SwipeVertical,
                    isChecked = preferences.useSwipeControls,
                    onClick = viewModel::toggleUseSwipeControls,
                    index = 1,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.zoom_gesture),
                    description = stringResource(id = R.string.zoom_gesture_description),
                    icon = DokiIcons.Pinch,
                    isChecked = preferences.useZoomControls,
                    onClick = viewModel::toggleUseZoomControls,
                    index = 2,
                    count = totalRows,
                )
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.double_tap),
                    description = stringResource(id = R.string.double_tap_description),
                    icon = DokiIcons.DoubleTap,
                    isChecked = (preferences.doubleTapGesture != DoubleTapGesture.NONE),
                    onChecked = viewModel::toggleDoubleTapGesture,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.DoubleTapDialog) },
                    index = 3,
                    count = totalRows,
                )
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.long_press_gesture),
                    description = stringResource(id = R.string.long_press_gesture_desc, preferences.longPressControlsSpeed),
                    icon = DokiIcons.Tap,
                    isChecked = preferences.useLongPressControls,
                    onChecked = viewModel::toggleUseLongPressControls,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.LongPressControlsSpeedDialog) },
                    index = 4,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(R.string.seek_increment),
                    description = stringResource(R.string.seconds, preferences.seekIncrement),
                    icon = DokiIcons.Replay,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.SeekIncrementDialog) },
                    index = 5,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(R.string.controller_timeout),
                    description = stringResource(R.string.seconds, preferences.controllerAutoHideTimeout),
                    icon = DokiIcons.Timer,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.ControllerTimeoutDialog) },
                    index = 6,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.control_buttons_alignment),
                    description = preferences.controlButtonsPosition.name(),
                    icon = DokiIcons.ButtonsPosition,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.ControlButtonsDialog) },
                    index = 7,
                    count = totalRows,
                )
            }
            ListSectionTitle(text = stringResource(id = R.string.playback))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                val totalRows = 8
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.resume),
                    description = stringResource(id = R.string.resume_description),
                    icon = DokiIcons.Resume,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.ResumeDialog) },
                    index = 0,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.default_playback_speed),
                    description = preferences.defaultPlaybackSpeed.toString(),
                    icon = DokiIcons.Speed,
                    onClick = { viewModel.showDialog(PlayerPreferenceDialog.PlaybackSpeedDialog) },
                    index = 1,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.autoplay_settings),
                    description = stringResource(
                        id = R.string.autoplay_settings_description,
                    ),
                    icon = DokiIcons.Player,
                    isChecked = preferences.autoplay,
                    onClick = viewModel::toggleAutoplay,
                    index = 2,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.pip_settings),
                    description = stringResource(
                        id = R.string.pip_settings_description,
                    ),
                    icon = DokiIcons.Pip,
                    isChecked = preferences.autoPip,
                    onClick = viewModel::toggleAutoPip,
                    index = 3,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.background_play),
                    description = stringResource(
                        id = R.string.background_play_description,
                    ),
                    icon = DokiIcons.Headset,
                    isChecked = preferences.autoBackgroundPlay,
                    onClick = viewModel::toggleAutoBackgroundPlay,
                    index = 4,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.remember_brightness_level),
                    description = stringResource(
                        id = R.string.remember_brightness_level_description,
                    ),
                    icon = DokiIcons.Brightness,
                    isChecked = preferences.rememberPlayerBrightness,
                    onClick = viewModel::toggleRememberBrightnessLevel,
                    index = 5,
                    count = totalRows,
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.remember_selections),
                    description = stringResource(id = R.string.remember_selections_description),
                    icon = DokiIcons.Selection,
                    isChecked = preferences.rememberSelections,
                    onClick = viewModel::toggleRememberSelections,
                    index = 6,
                    count = totalRows,
                )
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.player_screen_orientation),
                    description = preferences.playerScreenOrientation.name(),
                    icon = DokiIcons.Rotation,
                    onClick = {
                        viewModel.showDialog(PlayerPreferenceDialog.PlayerScreenOrientationDialog)
                    },
                    index = 7,
                    count = totalRows,
                )
            }
        }

        uiState.showDialog?.let { showDialog ->
            when (showDialog) {
                PlayerPreferenceDialog.ResumeDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.resume),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(Resume.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = (it == preferences.resume),
                                onClick = {
                                    viewModel.updatePlaybackResume(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                PlayerPreferenceDialog.DoubleTapDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.double_tap),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(DoubleTapGesture.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = (it == preferences.doubleTapGesture),
                                onClick = {
                                    viewModel.updateDoubleTapGesture(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                PlayerPreferenceDialog.PlayerScreenOrientationDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.player_screen_orientation),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(ScreenOrientation.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = it == preferences.playerScreenOrientation,
                                onClick = {
                                    viewModel.updatePreferredPlayerOrientation(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                PlayerPreferenceDialog.ControlButtonsDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.control_buttons_alignment),
                        onDismissClick = viewModel::hideDialog,
                    ) {
                        items(ControlButtonsPosition.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = it == preferences.controlButtonsPosition,
                                onClick = {
                                    viewModel.updatePreferredControlButtonsPosition(it)
                                    viewModel.hideDialog()
                                },
                            )
                        }
                    }
                }

                PlayerPreferenceDialog.PlaybackSpeedDialog -> {
                    var defaultPlaybackSpeed by remember {
                        mutableFloatStateOf(preferences.defaultPlaybackSpeed)
                    }

                    NextDialogWithDoneAndCancelButtons(
                        title = stringResource(R.string.default_playback_speed),
                        onDoneClick = {
                            viewModel.updateDefaultPlaybackSpeed(defaultPlaybackSpeed)
                            viewModel.hideDialog()
                        },
                        onDismissClick = viewModel::hideDialog,
                        content = {
                            Text(
                                text = "$defaultPlaybackSpeed",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = defaultPlaybackSpeed,
                                onValueChange = { defaultPlaybackSpeed = it.round(1) },
                                valueRange = 0.2f..4.0f,
                            )
                        },
                    )
                }

                PlayerPreferenceDialog.LongPressControlsSpeedDialog -> {
                    var longPressControlsSpeed by remember {
                        mutableFloatStateOf(preferences.longPressControlsSpeed)
                    }

                    NextDialogWithDoneAndCancelButtons(
                        title = stringResource(R.string.long_press_gesture),
                        onDoneClick = {
                            viewModel.updateLongPressControlsSpeed(longPressControlsSpeed)
                            viewModel.hideDialog()
                        },
                        onDismissClick = viewModel::hideDialog,
                        content = {
                            Text(
                                text = "$longPressControlsSpeed",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = longPressControlsSpeed,
                                onValueChange = { longPressControlsSpeed = it.round(1) },
                                valueRange = 0.2f..4.0f,
                            )
                        },
                    )
                }
                PlayerPreferenceDialog.ControllerTimeoutDialog -> {
                    var controllerAutoHideSec by remember {
                        mutableIntStateOf(preferences.controllerAutoHideTimeout)
                    }

                    NextDialogWithDoneAndCancelButtons(
                        title = stringResource(R.string.controller_timeout),
                        onDoneClick = {
                            viewModel.updateControlAutoHideTimeout(controllerAutoHideSec)
                            viewModel.hideDialog()
                        },
                        onDismissClick = viewModel::hideDialog,
                        content = {
                            Text(
                                text = stringResource(R.string.seconds, controllerAutoHideSec),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = controllerAutoHideSec.toFloat(),
                                onValueChange = { controllerAutoHideSec = it.toInt() },
                                valueRange = 1.0f..60.0f,
                            )
                        },
                    )
                }

                PlayerPreferenceDialog.SeekIncrementDialog -> {
                    var seekIncrement by remember {
                        mutableIntStateOf(preferences.seekIncrement)
                    }

                    NextDialogWithDoneAndCancelButtons(
                        title = stringResource(R.string.seek_increment),
                        onDoneClick = {
                            viewModel.updateSeekIncrement(seekIncrement)
                            viewModel.hideDialog()
                        },
                        onDismissClick = viewModel::hideDialog,
                        content = {
                            Text(
                                text = stringResource(R.string.seconds, seekIncrement),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = seekIncrement.toFloat(),
                                onValueChange = { seekIncrement = it.toInt() },
                                valueRange = 1.0f..60.0f,
                            )
                        },
                    )
                }
            }
        }
    }
}
