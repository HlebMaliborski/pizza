package com.devlopersquad.papacodes.presentation.di

import com.devlopersquad.papacodes.presentation.mapper.DomainToPresentationMapper
import com.devlopersquad.papacodes.presentation.mapper.DomainToPresentationMapperImpl
import com.devlopersquad.papacodes.presentation.viewmodel.CodeTutorialViewModel
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        CodeViewModel(
            getAllCodeUseCase = get(),
            getAllFilteredCodes = get(),
            storeCopiedCodeUseCase = get(),
            mapper = get(),
            navigator = get()
        )
    }

    viewModel {
        CodeTutorialViewModel(
            navigator = get(),
            setTutorialInfoUseCase = get()
        )
    }

    factory<DomainToPresentationMapper> { DomainToPresentationMapperImpl() }
}