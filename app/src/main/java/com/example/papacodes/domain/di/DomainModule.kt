package com.example.papacodes.domain.di

import com.example.papacodes.data.repository.CodeRepositoryImpl
import com.example.papacodes.domain.repository.CodeRepository
import com.example.papacodes.domain.usecase.GetAllCodeUseCase
import com.example.papacodes.domain.usecase.GetAllCodesUseCaseImpl
import com.example.papacodes.domain.usecase.GetAllFilteredCodes
import com.example.papacodes.domain.usecase.GetAllFilteredCodesImpl
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
}