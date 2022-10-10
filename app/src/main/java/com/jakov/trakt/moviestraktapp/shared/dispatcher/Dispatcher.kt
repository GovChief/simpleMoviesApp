package com.jakov.trakt.moviestraktapp.shared.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}