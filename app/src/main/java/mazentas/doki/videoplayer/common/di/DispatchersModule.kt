package mazentas.doki.videoplayer.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mazentas.doki.videoplayer.common.Dispatcher
import mazentas.doki.videoplayer.common.DokiDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(DokiDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(DokiDispatcher.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
