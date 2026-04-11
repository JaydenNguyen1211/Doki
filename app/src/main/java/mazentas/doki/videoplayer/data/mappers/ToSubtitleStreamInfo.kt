package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.database.entities.SubtitleStreamInfoEntity
import mazentas.doki.videoplayer.model.SubtitleStreamInfo

fun SubtitleStreamInfoEntity.toSubtitleStreamInfo() = SubtitleStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
)
