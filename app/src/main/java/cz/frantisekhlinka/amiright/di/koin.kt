package cz.frantisekhlinka.amiright.di

import android.content.Context
import cz.frantisekhlinka.amiright.backauth.backAuthModule
import cz.frantisekhlinka.amiright.backpost.backPostModule
import cz.frantisekhlinka.amiright.frontauth.frontAuthModule
import cz.frantisekhlinka.amiright.frontcreatepost.frontCreatePostModule
import cz.frantisekhlinka.amiright.fronthome.frontHomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication

fun KoinApplication.initKoin(appContext: Context) {
    androidLogger()
    androidContext(appContext)
    modules(appModule, backAuthModule, frontAuthModule, frontHomeModule, backPostModule, frontCreatePostModule)
}