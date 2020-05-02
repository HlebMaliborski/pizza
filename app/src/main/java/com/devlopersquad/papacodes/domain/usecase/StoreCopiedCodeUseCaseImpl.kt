package com.devlopersquad.papacodes.domain.usecase

import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.domain.repository.CodeRepository

class StoreCopiedCodeUseCaseImpl(private val repository: CodeRepository) :
    StoreCopiedCodeUseCase() {
    override suspend fun run(params: ParamsOfCopied): Either<Failure, String> {
        return when (val result = repository.storeCopiedCode(params.code)) {
            is Either.Error -> result
            is Either.Success -> Either.Success(result.data)
        }
    }
}

abstract class StoreCopiedCodeUseCase : BaseUseCase<String, ParamsOfCopied>()
data class ParamsOfCopied(val code: String)