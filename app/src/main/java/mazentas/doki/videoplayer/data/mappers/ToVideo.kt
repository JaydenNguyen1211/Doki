package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.common.Utils
import mazentas.doki.videoplayer.database.entities.AudioStreamInfoEntity
import mazentas.doki.videoplayer.database.entities.SubtitleStreamInfoEntity
import mazentas.doki.videoplayer.database.relations.MediumWithInfo
import mazentas.doki.videoplayer.model.Video
import java.util.Date

fun MediumWithInfo.toVideo() = Video(
    id = mediumEntity.mediaStoreId,
    path = mediumEntity.path,
    parentPath = mediumEntity.parentPath,
    duration = mediumEntity.duration,
    uriString = mediumEntity.uriString,
    nameWithExtension = mediumEntity.name,
    width = mediumEntity.width,
    height = mediumEntity.height,
    size = mediumEntity.size,
    dateModified = mediumEntity.modified,
    format = mediumEntity.format,
    thumbnailPath = mediumEntity.thumbnailPath,
    playbackPosition = mediumStateEntity?.playbackPosition ?: 0L,
    lastPlayedAt = mediumStateEntity?.lastPlayedTime?.let { Date(it) },
    formattedDuration = Utils.formatDurationMillis(mediumEntity.duration),
    formattedFileSize = Utils.formatFileSize(mediumEntity.size),
    videoStream = videoStreamInfo?.toVideoStreamInfo(),
    audioStreams = audioStreamsInfo.map(AudioStreamInfoEntity::toAudioStreamInfo),
    subtitleStreams = subtitleStreamsInfo.map(SubtitleStreamInfoEntity::toSubtitleStreamInfo),
)
