package com.nkoyo.componentidentifier.database.di

import android.content.Context
import androidx.room.Room
import com.nkoyo.componentidentifier.database.HistoryDao
import com.nkoyo.componentidentifier.database.NkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNkDatabase(@ApplicationContext context: Context): NkDatabase =
        Room.databaseBuilder(context.applicationContext, NkDatabase::class.java, "Nk-database")
            .build()

    @Provides
    @Singleton
    fun provideHistoryDao(db: NkDatabase): HistoryDao = db.historyDao()
}