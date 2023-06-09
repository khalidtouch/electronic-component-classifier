package com.nkoyo.componentidentifier.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.nkoyo.componentidentifier.UserPreferences
import com.nkoyo.componentidentifier.datastore.UserPreferencesSerializer
import com.nkoyo.componentidentifier.domain.util.Dispatcher
import com.nkoyo.componentidentifier.domain.util.NkDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer,
        @Dispatcher(NkDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}