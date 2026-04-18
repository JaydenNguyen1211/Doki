package mazentas.doki.videoplayer.ui.player.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.ui.theme.MediumIconSize

@Composable
fun PlayPauseButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberPlayPauseButtonState(player)
    val icon = when (state.showPlay) {
        true -> painterResource(R.drawable.ic_play)
        false -> painterResource(R.drawable.ic_pause)
    }
    val contentDescription = when (state.showPlay) {
        true -> stringResource(R.string.play_pause)
        false -> stringResource(R.string.play_pause)
    }

    PlayerButton(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        isEnabled = state.isEnabled,
        onClick = state::onClick,
        borderStroke = BorderStroke(1.dp, SolidColor(Color.White))
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(MediumIconSize),
        )
    }
}
