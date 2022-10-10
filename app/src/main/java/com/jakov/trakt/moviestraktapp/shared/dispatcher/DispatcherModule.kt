package com.jakov.trakt.moviestraktapp.shared.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    fun provideDispatcher(): Dispatcher = DispatcherImpl(Dispatchers.IO, Dispatchers.Default)
}