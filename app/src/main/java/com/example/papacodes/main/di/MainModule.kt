package com.example.papacodes.main.di

import com.example.papacodes.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(
            getInfoTutorialUseCase = get()
        )
    }
}