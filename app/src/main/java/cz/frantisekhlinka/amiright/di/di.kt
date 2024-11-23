package cz.frantisekhlinka.amiright.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import cz.frantisekhlinka.amiright.R
import cz.frantisekhlinka.amiright.coredata.GoogleWebClientId
import cz.frantisekhlinka.amiright.launcher.viewmodel.LauncherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseFunctions.getInstance() }
    single { GoogleWebClientId(androidContext().getString(R.string.default_web_client_id)) }

    viewModelOf(::LauncherViewModel)
}