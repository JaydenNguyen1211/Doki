package mazentas.doki.videoplayer.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import mazentas.doki.videoplayer.database.entities.AudioStreamInfoEntity
import mazentas.doki.videoplayer.database.entities.MediumEntity
import mazentas.doki.videoplayer.database.entities.MediumStateEntity
import mazentas.doki.videoplayer.database.entities.SubtitleStreamInfoEntity
import mazentas.doki.videoplayer.database.entities.VideoStreamInfoEntity

data class MediumWithInfo(
    @Embedded val mediumEntity: MediumEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "uri",
    )
    val mediumStateEntity: MediumStateEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val videoStreamInfo: VideoStreamInfoEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val audioStreamsInfo: List<AudioStreamInfoEntity>,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val subtitleStreamsInfo: List<SubtitleStreamInfoEntity>,
)
