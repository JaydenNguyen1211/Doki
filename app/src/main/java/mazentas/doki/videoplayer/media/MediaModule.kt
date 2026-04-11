package mazentas.doki.videoplayer.media

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mazentas.doki.videoplayer.media.services.LocalMediaService
import mazentas.doki.videoplayer.media.services.MediaService
import mazentas.doki.videoplayer.media.sync.LocalMediaInfoSynchronizer
import mazentas.doki.videoplayer.media.sync.LocalMediaSynchronizer
import mazentas.doki.videoplayer.media.sync.MediaInfoSynchronizer
import mazentas.doki.videoplayer.media.sync.MediaSynchronizer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    @Binds
    @Singleton
    fun bindsMediaSynchronizer(
        mediaSynchronizer: LocalMediaSynchronizer,
    ): MediaSynchronizer

    @Binds
    @Singleton
    fun bindsMediaInfoSynchronizer(
        mediaInfoSynchronizer: LocalMediaInfoSynchronizer,
    ): MediaInfoSynchronizer

    @Binds
    @Singleton
    fun bindMediaService(
        mediaService: LocalMediaService,
    ): MediaService
}
