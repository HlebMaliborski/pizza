package com.example.papacodes.domain.usecase

import com.example.core_common.result.Failure
import com.example.papacodes.common.response.Either
import com.example.papacodes.domain.repository.CodeRepository

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