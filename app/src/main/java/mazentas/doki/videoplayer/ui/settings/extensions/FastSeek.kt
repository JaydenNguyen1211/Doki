package mazentas.doki.videoplayer.ui.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.model.FastSeek

@Composable
fun FastSeek.name(): String {
    val stringRes = when (this) {
        FastSeek.AUTO -> R.string.auto
        FastSeek.ENABLE -> R.string.enable
        FastSeek.DISABLE -> R.string.disable
    }

    return stringResource(id = stringRes)
}
