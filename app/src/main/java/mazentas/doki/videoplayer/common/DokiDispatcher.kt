package mazentas.doki.videoplayer.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: DokiDispatcher)

enum class DokiDispatcher {
    Default,
    IO,
}
