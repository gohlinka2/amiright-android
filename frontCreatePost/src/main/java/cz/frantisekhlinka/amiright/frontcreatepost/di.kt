package cz.frantisekhlinka.amiright.frontcreatepost

import cz.frantisekhlinka.amiright.frontcreatepost.viewmodel.CreatePostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val frontCreatePostModule = module {
    viewModelOf(::CreatePostViewModel)
}