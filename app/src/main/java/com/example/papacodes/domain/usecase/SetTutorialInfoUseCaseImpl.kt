package com.example.papacodes.domain.usecase

import com.example.papacodes.common.response.Either
import com.example.papacodes.common.response.Failure
import com.example.papacodes.domain.repository.SharedRepository

class SetTutorialInfoUseCaseImpl(private val repository: SharedRepository) :
    SetTutorialInfoUseCase() {
    override suspend fun run(params: None): Either<Failure, Boolean> {
        repository.tutorialPassed()
        return Either.Success(true)
    }
}

abstract class SetTutorialInfoUseCase : BaseUseCase<Boolean, BaseUseCase.None>()
