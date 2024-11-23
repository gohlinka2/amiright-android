package cz.frantisekhlinka.amiright.frontauth

import cz.frantisekhlinka.amiright.frontauth.viewmodel.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val frontAuthModule = module {
    viewModelOf(::AuthViewModel)
}