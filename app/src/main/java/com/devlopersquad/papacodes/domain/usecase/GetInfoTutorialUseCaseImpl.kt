package com.devlopersquad.papacodes.domain.usecase

import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.domain.repository.SharedRepository

class GetInfoTutorialUseCaseImpl(private val repository: SharedRepository) :
    GetInfoTutorialUseCase() {
    override suspend fun run(params: None): Either<Failure, Boolean> {
        return when (val result = repository.isTutorialPassed()) {
            is Either.Error -> result
            is Either.Success -> Either.Success(result.data)
        }
    }
}

abstract class GetInfoTutorialUseCase : BaseUseCase<Boolean, BaseUseCase.None>()