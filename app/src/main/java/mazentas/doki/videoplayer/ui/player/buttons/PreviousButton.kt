package mazentas.doki.videoplayer.ui.player.buttons

import androidx.annotation.OptIn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPreviousButtonState
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.player.LocalControlsVisibilityState

@OptIn(UnstableApi::class)
@Composable
internal fun PreviousButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberPreviousButtonState(player)
    val controlsVisibilityState = LocalControlsVisibilityState.current

    PlayerButton(
        modifier = modifier,
        isEnabled = state.isEnabled,
        onClick = {
            state.onClick()
            controlsVisibilityState?.showControls()
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_skip_prev),
            contentDescription = stringResource(R.string.player_controls_previous),
        )
    }
}
