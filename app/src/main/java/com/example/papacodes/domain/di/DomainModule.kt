package com.example.papacodes.domain.di

import com.example.papacodes.data.repository.CodeRepositoryImpl
import com.example.papacodes.domain.repository.CodeRepository
import com.example.papacodes.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    single<CodeRepository> {
        CodeRepositoryImpl(
            networkDataSource = get(),
            cacheDataSource = get(),
            mapper = get(),
            networkManager = get()
        )
    }

    factory<GetAllCodeUseCase> { GetAllCodesUseCaseImpl(repository = get()) }
    factory<GetAllFilteredCodes> { GetAllFilteredCodesImpl(repository = get()) }
    factory<StoreCopiedCodeUseCase> { StoreCopiedCodeUseCaseImpl(repository = get()) }
}