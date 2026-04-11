package mazentas.doki.videoplayer.media.sync

import android.net.Uri

interface MediaInfoSynchronizer {

    fun sync(uri: Uri)
}
