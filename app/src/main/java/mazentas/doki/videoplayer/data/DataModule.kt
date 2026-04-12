package mazentas.doki.videoplayer.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mazentas.doki.videoplayer.data.repository.LocalMediaRepository
import mazentas.doki.videoplayer.data.repository.LocalPreferencesRepository
import mazentas.doki.videoplayer.data.repository.MediaRepository
import mazentas.doki.videoplayer.data.repository.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsMediaRepository(
        videoRepository: LocalMediaRepository,
    ): MediaRepository

    @Binds
    fun bindsPreferencesRepository(
        preferencesRepository: LocalPreferencesRepository,
    ): PreferencesRepository
}
