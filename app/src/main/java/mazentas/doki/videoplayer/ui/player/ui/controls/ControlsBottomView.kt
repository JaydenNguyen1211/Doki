package mazentas.doki.videoplayer.ui.player.ui.controls

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.model.VideoContentScale
import mazentas.doki.videoplayer.ui.player.buttons.LoopButton
import mazentas.doki.videoplayer.ui.player.buttons.NextButton
import mazentas.doki.videoplayer.ui.player.buttons.PlayPauseButton
import mazentas.doki.videoplayer.ui.player.buttons.PlayerButton
import mazentas.doki.videoplayer.ui.player.buttons.PreviousButton
import mazentas.doki.videoplayer.ui.player.extensions.drawableRes
import mazentas.doki.videoplayer.ui.player.extensions.noRippleClickable
import mazentas.doki.videoplayer.ui.player.state.MediaPresentationState
import mazentas.doki.videoplayer.ui.player.state.durationFormatted
import mazentas.doki.videoplayer.ui.player.state.pendingPositionFormatted
import mazentas.doki.videoplayer.ui.player.state.positionFormatted
import mazentas.doki.videoplayer.ui.theme.DefaultIconSize
import mazentas.doki.videoplayer.ui.theme.MediumIconSize
import mazentas.doki.videoplayer.ui.theme.backgroundPlayButtonColor
import mazentas.doki.videoplayer.ui.theme.primaryLight

@OptIn(UnstableApi::class)
@Composable
fun ControlOptionOverlayView(
    modifier: Modifier = Modifier,
    player: Player,
    controlsAlignment: Alignment.Horizontal,
    isPipSupported: Boolean,
    onRotateClick: () -> Unit,
    onPictureInPictureClick: () -> Unit,
    onPlayInBackgroundClick: () -> Unit,
    onPlaybackSpeedClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = controlsAlignment),
    ) {

        PlayerButton(
            modifier = modifier,
            onClick = onRotateClick,
            containerColor = backgroundPlayButtonColor
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_screen_rotation),
                contentDescription = null,
                modifier = Modifier.size(DefaultIconSize)
            )
        }
        if (isPipSupported) {
            PlayerButton(onClick = onPictureInPictureClick, containerColor = backgroundPlayButtonColor) {
                Icon(
                    painter = painterResource(R.drawable.ic_pip),
                    contentDescription = null,
                    modifier = Modifier.size(DefaultIconSize)
                )
            }
        }
        PlayerButton(onClick = onPlaybackSpeedClick,  containerColor = backgroundPlayButtonColor) {
            Icon(
                painter = painterResource(R.drawable.ic_speed),
                contentDescription = null,
                modifier = Modifier.size(DefaultIconSize)
            )
        }
        PlayerButton(onClick = onPlayInBackgroundClick, containerColor = backgroundPlayButtonColor) {
            Icon(
                painter = painterResource(R.drawable.ic_headset),
                contentDescription = null,
                modifier = Modifier.size(DefaultIconSize)
            )
        }
        LoopButton(player = player)
    }
}

@Composable
fun ControlsPlayView(modifier: Modifier = Modifier, player: Player, mediaPresentationState: MediaPresentationState,onSeek: (Long) -> Unit,
                     onSeekEnd: () -> Unit,   controlsAlignment: Alignment.Horizontal,
                     videoContentScale: VideoContentScale,
                     onVideoContentScaleClick: () -> Unit,
                     onVideoContentScaleLongClick: () -> Unit,
                     onLockControlsClick: () -> Unit,
) {

   Column (modifier = modifier.padding(8.dp)){
       var showPendingPosition by rememberSaveable { mutableStateOf(false) }

      Row(
          modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = controlsAlignment),) {
          Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.noRippleClickable {
                  showPendingPosition = !showPendingPosition
              },
          ) {
              Text(
                  text = when (showPendingPosition) {
                      true -> "-${mediaPresentationState.pendingPositionFormatted}"
                      false -> mediaPresentationState.positionFormatted
                  },
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White,
              )
              Text(
                  text = " / ",
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White,
              )
              Text(
                  text = mediaPresentationState.durationFormatted,
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White,
              )
          }

          Spacer(modifier = Modifier.weight(1f))
      }

       PlayerSeekbar(
           position = mediaPresentationState.position.toFloat(),
           duration = mediaPresentationState.duration.toFloat(),
           onSeek = { onSeek(it.toLong()) },
           onSeekFinished = { onSeekEnd() },
       )

       Row(
           modifier = modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterHorizontally),
           verticalAlignment = Alignment.CenterVertically,
       ) {
           PlayerButton(onClick = onLockControlsClick) {
               Icon(
                   painter = painterResource(R.drawable.ic_lock_open),
                   contentDescription = null,
                   modifier = Modifier.size(MediumIconSize)
               )
           }
           PreviousButton(player = player)
           PlayPauseButton(player = player)
           NextButton(player = player)

           PlayerButton(
               onClick = onVideoContentScaleClick,
               onLongClick = onVideoContentScaleLongClick
           ) {
               Icon(
                   painter = painterResource(videoContentScale.drawableRes()),
                   contentDescription = null,
                   modifier = Modifier.size(MediumIconSize)
               )
           }
       }
   }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerSeekbar(
    modifier: Modifier = Modifier,
    position: Float,
    duration: Float,
    onSeek: (Float) -> Unit,
    onSeekFinished: () -> Unit,
) {
    val primaryColor = primaryLight
    Log.d("testColor ", "PlayerSeekbar: color ${primaryColor.value.toString()} ")
    val interactionSource = remember { MutableInteractionSource() }
    val trackHeight = 10.dp
    val thumbWidth = 4.dp
    val trackThumbGapWidth = 12.dp

    Slider(
        value = position,
        valueRange = 0f..duration,
        onValueChange = onSeek,
        onValueChangeFinished = onSeekFinished,
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth(),
        track = { sliderState ->
            val disabledAlpha = 0.4f

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight),
            ) {
                val min = sliderState.valueRange.start
                val max = sliderState.valueRange.endInclusive
                val range = (max - min).takeIf { it > 0f } ?: 1f
                val playedFraction = ((sliderState.value - min) / range).coerceIn(0f, 1f)
                val playedPixels = size.width * playedFraction

                val endCornerRadius = size.height / 2f
                val insideCornerRadius = 2.dp.toPx()
                val gapHalf = trackThumbGapWidth.toPx() / 2f
                val leftEnd = (playedPixels - gapHalf).coerceIn(0f, size.width)
                val rightStart = (playedPixels + gapHalf).coerceIn(0f, size.width)

                // Inactive track left side
                if (leftEnd > 0f) {
                    drawRoundedRect(
                        offset = Offset(0f, 0f),
                        size = Size(leftEnd, size.height),
                        color = primaryColor.copy(alpha = disabledAlpha),
                        startCornerRadius = endCornerRadius,
                        endCornerRadius = insideCornerRadius,
                    )
                }

                // Inactive track right side
                if (rightStart < size.width) {
                    drawRoundedRect(
                        offset = Offset(rightStart, 0f),
                        size = Size(size.width - rightStart, size.height),
                        color = primaryColor.copy(alpha = disabledAlpha),
                        startCornerRadius = insideCornerRadius,
                        endCornerRadius = endCornerRadius,
                    )
                }

                // Active track
                if (leftEnd > 0f) {
                    drawRoundedRect(
                        offset = Offset(0f, 0f),
                        size = Size(leftEnd, size.height),
                        color = primaryColor,
                        startCornerRadius = endCornerRadius,
                        endCornerRadius = insideCornerRadius,
                    )
                }
            }
        },
        thumb = {
            Box(
                modifier = Modifier
                    .width(thumbWidth)
                    .height(24.dp)
                    .background(primaryColor, CircleShape),
            )
        },
    )
}

private fun DrawScope.drawRoundedRect(
    offset: Offset,
    size: Size,
    color: Color,
    startCornerRadius: Float,
    endCornerRadius: Float,
) {
    val startCorner = CornerRadius(startCornerRadius, startCornerRadius)
    val endCorner = CornerRadius(endCornerRadius, endCornerRadius)
    val track = RoundRect(
        rect = Rect(Offset(offset.x, 0f), size = Size(size.width, size.height)),
        topLeft = startCorner,
        topRight = endCorner,
        bottomRight = endCorner,
        bottomLeft = startCorner,
    )
    drawPath(
        path = Path().apply {
            addRoundRect(track)
        },
        color = color,
    )
}
