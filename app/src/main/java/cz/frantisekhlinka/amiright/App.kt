package cz.frantisekhlinka.amiright

import android.app.Application
import cz.frantisekhlinka.amiright.di.initKoin
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            initKoin(this@App)
        }
    }
}