package com.nkoyo.componentidentifier.domain.util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Dispatcher(NkDispatcher.IO)
    fun providesIODispatchers() : CoroutineDispatcher = Dispatchers.IO
}