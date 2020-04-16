package com.example.papacodes.presentation.di

import com.example.papacodes.presentation.mapper.DomainToPresentationMapper
import com.example.papacodes.presentation.mapper.DomainToPresentationMapperImpl
import com.example.papacodes.presentation.viewmodel.CodeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        CodeViewModel(
            getAllCodeUseCase = get(),
            getAllCodesFilteredByCity = get(),
            storeCopiedCodeUseCase = get(),
            mapper = get(),
            navigator = get()
        )
    }

    factory<DomainToPresentationMapper> { DomainToPresentationMapperImpl() }
}