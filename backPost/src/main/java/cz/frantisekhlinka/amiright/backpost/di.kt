package cz.frantisekhlinka.amiright.backpost

import cz.frantisekhlinka.amiright.backpost.api.PostApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val backPostModule = module {
    singleOf(::PostApi)
}