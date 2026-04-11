package mazentas.doki.videoplayer.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import mazentas.doki.videoplayer.database.entities.DirectoryEntity
import mazentas.doki.videoplayer.database.entities.MediumEntity

data class DirectoryWithMedia(
    @Embedded val directory: DirectoryEntity,
    @Relation(
        entity = MediumEntity::class,
        parentColumn = "path",
        entityColumn = "parent_path",
    )
    val media: List<MediumWithInfo>,
)
