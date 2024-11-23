package cz.frantisekhlinka.amiright.di

import cz.frantisekhlinka.amiright.backauth.backAuthModule
import cz.frantisekhlinka.amiright.frontauth.frontAuthModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

fun KoinApplication.initKoin() {
    androidLogger()
    modules(appModule, backAuthModule, frontAuthModule)
}