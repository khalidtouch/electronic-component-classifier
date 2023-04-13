package com.nkoyo.componentidentifier.data.di

import com.nkoyo.componentidentifier.data.classifier.ComponentClassifierImpl
import com.nkoyo.componentidentifier.domain.classifier.ComponentClassifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {
    @Binds
    @Singleton
    abstract fun provideComponentClassifier(impl: ComponentClassifierImpl): ComponentClassifier
}