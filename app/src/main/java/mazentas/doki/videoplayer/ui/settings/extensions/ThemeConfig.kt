package mazentas.doki.videoplayer.ui.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import mazentas.doki.videoplayer.R
import mazentas.doki.videoplayer.model.ThemeConfig

@Composable
fun ThemeConfig.name(): String {
    val stringRes = when (this) {
        ThemeConfig.SYSTEM -> R.string.system_default
        ThemeConfig.OFF -> R.string.off
        ThemeConfig.ON -> R.string.on
    }

    return stringResource(id = stringRes)
}
