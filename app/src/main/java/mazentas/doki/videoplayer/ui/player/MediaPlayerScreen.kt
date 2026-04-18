package mazentas.doki.videoplayer.ui.player

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.model.ControlButtonsPosition
import mazentas.doki.videoplayer.model.PlayerPreferences
import mazentas.doki.videoplayer.ui.player.buttons.LoopButton
import mazentas.doki.videoplayer.ui.player.buttons.NextButton
import mazentas.doki.videoplayer.ui.player.buttons.PlayPauseButton
import mazentas.doki.videoplayer.ui.player.buttons.PlayerButton
import mazentas.doki.videoplayer.ui.player.buttons.PreviousButton
import mazentas.doki.videoplayer.ui.player.extensions.drawableRes
import mazentas.doki.videoplayer.ui.player.state.ControlsVisibilityState
import mazentas.doki.videoplayer.ui.player.state.MediaPresentationState
import mazentas.doki.videoplayer.ui.player.state.rememberBrightnessState
import mazentas.doki.videoplayer.ui.player.state.rememberControlsVisibilityState
import mazentas.doki.videoplayer.ui.player.state.rememberMediaPresentationState
import mazentas.doki.videoplayer.ui.player.state.rememberMetadataState
import mazentas.doki.videoplayer.ui.player.state.rememberPictureInPictureState
import mazentas.doki.videoplayer.ui.player.state.rememberRotationState
import mazentas.doki.videoplayer.ui.player.state.rememberSeekGestureState
import mazentas.doki.videoplayer.ui.player.state.rememberTapGesureState
import mazentas.doki.videoplayer.ui.player.state.rememberVideoZoomAndContentScaleState
import mazentas.doki.videoplayer.ui.player.state.rememberVolumeAndBrightnessGestureState
import mazentas.doki.videoplayer.ui.player.state.rememberVolumeState
import mazentas.doki.videoplayer.ui.player.state.seekAmountFormatted
import mazentas.doki.videoplayer.ui.player.state.seekToPositionFormated
import mazentas.doki.videoplayer.ui.player.ui.DoubleTapIndicator
import mazentas.doki.videoplayer.ui.player.ui.OverlayShowView
import mazentas.doki.videoplayer.ui.player.ui.OverlayView
import mazentas.doki.videoplayer.ui.player.ui.SubtitleConfiguration
import mazentas.doki.videoplayer.ui.player.ui.VerticalProgressView
import mazentas.doki.videoplayer.ui.player.ui.controls.ControlOptionOverlayView
import mazentas.doki.videoplayer.ui.player.ui.controls.ControlsPlayView
import mazentas.doki.videoplayer.ui.player.ui.controls.ControlsTopView
import kotlin.time.Duration.Companion.seconds

val LocalControlsVisibilityState = compositionLocalOf<ControlsVisibilityState?> { null }

@OptIn(UnstableApi::class)
@Composable
fun MediaPlayerScreen(
    player: MediaController,
    viewModel: PlayerViewModel,
    playerPreferences: PlayerPreferences,
    modifier: Modifier = Modifier,
    onSelectSubtitleClick: () -> Unit,
    onBackClick: () -> Unit,
    onPlayInBackgroundClick: () -> Unit,
) {
    val metadataState = rememberMetadataState(player)
    val mediaPresentationState = rememberMediaPresentationState(player)
    val controlsVisibilityState = rememberControlsVisibilityState(
        player = player,
        hideAfter = playerPreferences.controllerAutoHideTimeout.seconds,
    )
    val tapGestureState = rememberTapGesureState(
        player = player,
        doubleTapGesture = playerPreferences.doubleTapGesture,
        seekIncrementMillis = playerPreferences.seekIncrement.seconds.inWholeMilliseconds,
        useLongPressGesture = playerPreferences.useLongPressControls,
        longPressSpeed = playerPreferences.longPressControlsSpeed,
    )
    val seekGestureState = rememberSeekGestureState(player = player)
    val pictureInPictureState = rememberPictureInPictureState(
        player = player,
        autoEnter = playerPreferences.autoPip,
    )
    val videoZoomAndContentScaleState = rememberVideoZoomAndContentScaleState(
        player = player,
        initialContentScale = playerPreferences.playerVideoZoom,
        onEvent = viewModel::onVideoZoomEvent,
    )
    val volumeState = rememberVolumeState(
        showVolumePanelIfHeadsetIsOn = playerPreferences.showSystemVolumePanel,
    )
    val brightnessState = rememberBrightnessState()
    val volumeAndBrightnessGestureState = rememberVolumeAndBrightnessGestureState(
        showVolumePanelIfHeadsetIsOn = playerPreferences.showSystemVolumePanel,
    )
    val rotationState = rememberRotationState(
        player = player,
        screenOrientation = playerPreferences.playerScreenOrientation,
    )

    LaunchedEffect(pictureInPictureState.isInPictureInPictureMode) {
        if (pictureInPictureState.isInPictureInPictureMode) {
            controlsVisibilityState.hideControls()
        }
    }

    LaunchedEffect(tapGestureState.isLongPressGestureInAction) {
        if (tapGestureState.isLongPressGestureInAction) {
            controlsVisibilityState.hideControls()
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        if (playerPreferences.rememberPlayerBrightness) {
            brightnessState.setBrightness(playerPreferences.playerBrightness)
        }
    }

    LaunchedEffect(brightnessState.currentBrightness) {
        if (playerPreferences.rememberPlayerBrightness) {
            viewModel.updatePlayerBrightness(brightnessState.currentBrightness)
        }
    }

    var overlayView by remember { mutableStateOf<OverlayView?>(null) }

    CompositionLocalProvider(LocalControlsVisibilityState provides controlsVisibilityState) {
        Box {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .background(Color.Black),
            ) {
                PlayerContentFrame(
                    player = player,
                    pictureInPictureState = pictureInPictureState,
                    controlsVisibilityState = controlsVisibilityState,
                    tapGestureState = tapGestureState,
                    seekGestureState = seekGestureState,
                    videoZoomAndContentScaleState = videoZoomAndContentScaleState,
                    volumeAndBrightnessGestureState = volumeAndBrightnessGestureState,
                    subtitleConfiguration = SubtitleConfiguration(
                        useSystemCaptionStyle = playerPreferences.useSystemCaptionStyle,
                        showBackground = playerPreferences.subtitleBackground,
                        font = playerPreferences.subtitleFont,
                        textSize = playerPreferences.subtitleTextSize,
                        textBold = playerPreferences.subtitleTextBold,
                        applyEmbeddedStyles = playerPreferences.applyEmbeddedStyles,
                    ),
                )

                if (mediaPresentationState.isBuffering) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(72.dp),
                    )
                }

                DoubleTapIndicator(tapGestureState = tapGestureState)

                AnimatedVisibility(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.TopCenter),
                    visible = tapGestureState.isLongPressGestureInAction,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Surface(shape = CircleShape) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            ),
                        ) {
                            Text(
                                text = stringResource(R.string.fast_playback_speed, tapGestureState.longPressSpeed),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }

                if (controlsVisibilityState.controlsVisible && controlsVisibilityState.controlsLocked) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding()
                            .padding(top = 24.dp),
                    ) {
                        PlayerButton(onClick = { controlsVisibilityState.unlockControls() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_lock),
                                contentDescription = stringResource(R.string.controls_unlock),
                            )
                        }
                    }
                } else {
                    PlayerControlsView(
                        topView = {
                            AnimatedVisibility(
                                visible = controlsVisibilityState.controlsVisible,
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                Column(
                                ) {
                                    val context = LocalContext.current
                                    ControlsTopView(
                                        title = metadataState.title ?: "",
                                        onAudioClick = {
                                            controlsVisibilityState.hideControls()
                                            overlayView = OverlayView.AUDIO_SELECTOR
                                        },
                                        onSubtitleClick = {
                                            controlsVisibilityState.hideControls()
                                            overlayView = OverlayView.SUBTITLE_SELECTOR
                                        },
                                        onPlaybackSpeedClick = {
                                            controlsVisibilityState.hideControls()
                                            overlayView = OverlayView.PLAYBACK_SPEED
                                        },
                                        onBackClick = onBackClick,
                                    )

                                    ControlOptionOverlayView(
                                    player = player,
                                    controlsAlignment = when (playerPreferences.controlButtonsPosition) {
                                        ControlButtonsPosition.LEFT -> Alignment.Start
                                        ControlButtonsPosition.RIGHT -> Alignment.End
                                    },
                                    videoContentScale = videoZoomAndContentScaleState.videoContentScale,
                                    isPipSupported = pictureInPictureState.isPipSupported,
                                    onPlayInBackgroundClick = onPlayInBackgroundClick,
                                    onLockControlsClick = {
                                        controlsVisibilityState.showControls()
                                        controlsVisibilityState.lockControls()
                                    },
                                    onVideoContentScaleClick = {
                                        controlsVisibilityState.showControls()
                                        videoZoomAndContentScaleState.switchToNextVideoContentScale()
                                    },
                                    onVideoContentScaleLongClick = {
                                        controlsVisibilityState.hideControls()
                                        overlayView = OverlayView.VIDEO_CONTENT_SCALE
                                    },
                                    onPictureInPictureClick = {
                                        if (!pictureInPictureState.hasPipPermission) {
                                            Toast.makeText(context, R.string.enable_pip_from_settings, Toast.LENGTH_SHORT).show()
                                            pictureInPictureState.openPictureInPictureSettings()
                                        } else {
                                            pictureInPictureState.enterPictureInPictureMode()
                                        }
                                    },
                                )
                                }
                            }
                        },
                        middleView = {
                        },
                        bottomView = {
                            AnimatedVisibility(
                                visible = controlsVisibilityState.controlsVisible && !controlsVisibilityState.controlsLocked,
                                enter = fadeIn(),
                                exit = fadeOut(),
                            ) {
                                val context = LocalContext.current
                                ControlsPlayView(player = player,    mediaPresentationState = mediaPresentationState,
                                    onSeek = seekGestureState::onSeek,
                                    onSeekEnd = seekGestureState::onSeekEnd,
                                    onRotateClick = rotationState::rotate,
                                    controlsAlignment = when (playerPreferences.controlButtonsPosition) {
                                        ControlButtonsPosition.LEFT -> Alignment.Start
                                        ControlButtonsPosition.RIGHT -> Alignment.End
                                    })
//                                ControlsBottomView(
//                                    player = player,
//                                    mediaPresentationState = mediaPresentationState,
//                                    controlsAlignment = when (playerPreferences.controlButtonsPosition) {
//                                        ControlButtonsPosition.LEFT -> Alignment.Start
//                                        ControlButtonsPosition.RIGHT -> Alignment.End
//                                    },
//                                    videoContentScale = videoZoomAndContentScaleState.videoContentScale,
//                                    isPipSupported = pictureInPictureState.isPipSupported,
//                                    onSeek = seekGestureState::onSeek,
//                                    onSeekEnd = seekGestureState::onSeekEnd,
//                                    onRotateClick = rotationState::rotate,
//                                    onPlayInBackgroundClick = onPlayInBackgroundClick,
//                                    onLockControlsClick = {
//                                        controlsVisibilityState.showControls()
//                                        controlsVisibilityState.lockControls()
//                                    },
//                                    onVideoContentScaleClick = {
//                                        controlsVisibilityState.showControls()
//                                        videoZoomAndContentScaleState.switchToNextVideoContentScale()
//                                    },
//                                    onVideoContentScaleLongClick = {
//                                        controlsVisibilityState.hideControls()
//                                        overlayView = OverlayView.VIDEO_CONTENT_SCALE
//                                    },
//                                    onPictureInPictureClick = {
//                                        if (!pictureInPictureState.hasPipPermission) {
//                                            Toast.makeText(context, R.string.enable_pip_from_settings, Toast.LENGTH_SHORT).show()
//                                            pictureInPictureState.openPictureInPictureSettings()
//                                        } else {
//                                            pictureInPictureState.enterPictureInPictureMode()
//                                        }
//                                    },
//                                )
                            }
                        },
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .displayCutoutPadding()
                        .padding(24.dp),
                ) {
                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.CenterStart),
                        visible = volumeAndBrightnessGestureState.volumeChangePercentage != 0,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        VerticalProgressView(
                            value = volumeState.volumePercentage,
                            icon = painterResource(R.drawable.ic_volume),
                        )
                    }

                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        visible = volumeAndBrightnessGestureState.brightnessChangePercentage != 0,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        VerticalProgressView(
                            value = brightnessState.brightnessPercentage,
                            icon = painterResource(R.drawable.ic_brightness),
                        )
                    }
                }
            }

            OverlayShowView(
                player = player,
                overlayView = overlayView,
                videoContentScale = videoZoomAndContentScaleState.videoContentScale,
                onDismiss = { overlayView = null },
                onSelectSubtitleClick = onSelectSubtitleClick,
                onVideoContentScaleChanged = { videoZoomAndContentScaleState.onVideoContentScaleChanged(it) },
            )
        }
    }

    BackHandler {
        if (overlayView != null) {
            overlayView = null
        } else {
            onBackClick()
        }
    }
}

@Composable
fun InfoView(
    modifier: Modifier = Modifier,
    info: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = info,
            style = textStyle,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}



@Composable
fun PlayerControlsView(
    modifier: Modifier = Modifier,
    topView: @Composable () -> Unit,
    middleView: @Composable BoxScope.() -> Unit,
    bottomView: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            topView()
            Spacer(modifier = Modifier.weight(1f))
            bottomView()
        }

        middleView()
    }
}
