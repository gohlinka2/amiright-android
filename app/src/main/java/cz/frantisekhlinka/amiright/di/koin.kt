package cz.frantisekhlinka.amiright.di

import android.content.Context
import cz.frantisekhlinka.amiright.backauth.backAuthModule
import cz.frantisekhlinka.amiright.frontauth.frontAuthModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

fun KoinApplication.initKoin(appContext: Context) {
    androidLogger()
    androidContext(appContext)
    modules(appModule, backAuthModule, frontAuthModule)
}