package com.jperez.timerapp

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


var koinModule = module {
    viewModel<MainViewModel> {
        MainViewModel()
    }
}