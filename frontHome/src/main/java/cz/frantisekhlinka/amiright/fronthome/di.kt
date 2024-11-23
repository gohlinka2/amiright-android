package cz.frantisekhlinka.amiright.fronthome

import cz.frantisekhlinka.amiright.fronthome.viewmodel.FeedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val frontHomeModule = module {
    viewModelOf(::FeedViewModel)
}