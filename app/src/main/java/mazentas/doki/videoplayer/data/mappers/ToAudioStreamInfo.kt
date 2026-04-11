package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.database.entities.AudioStreamInfoEntity
import mazentas.doki.videoplayer.model.AudioStreamInfo

fun AudioStreamInfoEntity.toAudioStreamInfo() = AudioStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    sampleFormat = sampleFormat,
    sampleRate = sampleRate,
    channels = channels,
    channelLayout = channelLayout,
)
