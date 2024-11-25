package cz.frantisekhlinka.amiright.backauth

import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import cz.frantisekhlinka.amiright.backauth.repo.AuthRepo
import cz.frantisekhlinka.amiright.coreback.repo.IAuthStateRepo
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val backAuthModule = module {
    single { FirebaseAuth.getInstance() }
    single { CredentialManager.create(get()) }
    // bind both as AuthRepo and IAuthStateRepo
    singleOf(::AuthRepo) bind IAuthStateRepo::class
}