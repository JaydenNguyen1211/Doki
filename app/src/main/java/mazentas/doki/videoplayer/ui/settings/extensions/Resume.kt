package mazentas.doki.videoplayer.ui.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.model.Resume

@Composable
fun Resume.name(): String {
    val stringRes = when (this) {
        Resume.YES -> R.string.yes
        Resume.NO -> R.string.no
    }

    return stringResource(id = stringRes)
}
