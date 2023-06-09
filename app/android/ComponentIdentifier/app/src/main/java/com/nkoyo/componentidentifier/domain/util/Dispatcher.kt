package com.nkoyo.componentidentifier.domain.util

import javax.inject.Qualifier


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val dispatcher: NkDispatcher) {
}

enum class NkDispatcher { IO }