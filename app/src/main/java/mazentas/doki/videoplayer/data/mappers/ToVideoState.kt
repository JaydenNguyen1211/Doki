package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.data.models.VideoState
import mazentas.doki.videoplayer.database.converter.UriListConverter
import mazentas.doki.videoplayer.database.entities.MediumStateEntity

fun MediumStateEntity.toVideoState(): VideoState {
    return VideoState(
        path = uriString,
        position = playbackPosition.takeIf { it != 0L },
        audioTrackIndex = audioTrackIndex,
        subtitleTrackIndex = subtitleTrackIndex,
        playbackSpeed = playbackSpeed,
        externalSubs = UriListConverter.fromStringToList(externalSubs),
        videoScale = videoScale,
    )
}
