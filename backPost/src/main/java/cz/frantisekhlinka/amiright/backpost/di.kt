package cz.frantisekhlinka.amiright.backpost

import cz.frantisekhlinka.amiright.backpost.api.PostApi
import cz.frantisekhlinka.amiright.backpost.repo.PostRepoImpl
import cz.frantisekhlinka.amiright.coreback.repo.PostRepo
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val backPostModule = module {
    singleOf(::PostApi)
    singleOf(::PostRepoImpl) { bind<PostRepo>() }
}