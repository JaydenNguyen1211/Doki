package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.database.entities.VideoStreamInfoEntity
import mazentas.doki.videoplayer.model.VideoStreamInfo

fun VideoStreamInfoEntity.toVideoStreamInfo() = VideoStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    frameRate = frameRate,
    frameWidth = frameWidth,
    frameHeight = frameHeight,
)
