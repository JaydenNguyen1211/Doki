package mazentas.doki.videoplayer.ui.player.buttons

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberNextButtonState
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.player.LocalControlsVisibilityState
import mazentas.doki.videoplayer.ui.theme.MediumIconSize

@OptIn(UnstableApi::class)
@Composable
internal fun NextButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberNextButtonState(player)
    val controlsVisibilityState = LocalControlsVisibilityState.current

    PlayerButton(
        modifier = modifier,
        isEnabled = state.isEnabled,
        contentPadding = PaddingValues(16.dp),
        onClick = {
            state.onClick()
            controlsVisibilityState?.showControls()
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_skip_next),
            contentDescription = stringResource(R.string.player_controls_next),
            modifier = Modifier.size(MediumIconSize),
        )
    }
}
