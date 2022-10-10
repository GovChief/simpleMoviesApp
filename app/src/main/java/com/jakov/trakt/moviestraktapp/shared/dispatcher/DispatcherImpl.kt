package com.jakov.trakt.moviestraktapp.shared.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

data class DispatcherImpl @Inject constructor(
    override val io: CoroutineDispatcher,
    override val default: CoroutineDispatcher,
): Dispatcher