package com.devlopersquad.papacodes.domain.di

import com.devlopersquad.papacodes.data.repository.CodeRepositoryImpl
import com.devlopersquad.papacodes.domain.repository.CodeRepository
import com.devlopersquad.papacodes.domain.usecase.*
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
    factory<GetInfoTutorialUseCase> { GetInfoTutorialUseCaseImpl(repository = get()) }
    factory<SetTutorialInfoUseCase> { SetTutorialInfoUseCaseImpl(repository = get()) }
}