package mazentas.doki.videoplayer.data.mappers

import mazentas.doki.videoplayer.common.Utils
import mazentas.doki.videoplayer.database.relations.DirectoryWithMedia
import mazentas.doki.videoplayer.database.relations.MediumWithInfo
import mazentas.doki.videoplayer.model.Folder

fun DirectoryWithMedia.toFolder() = Folder(
    name = directory.name,
    path = directory.path,
    dateModified = directory.modified,
    parentPath = directory.parentPath,
    formattedMediaSize = Utils.formatFileSize(media.sumOf { it.mediumEntity.size }),
    mediaList = media.map(MediumWithInfo::toVideo),
)
