package com.example.papacodes.domain.usecase

import com.example.papacodes.common.response.Failure
import com.example.papacodes.common.response.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Type, in Params> where Type : Any {
    open suspend operator fun invoke(
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        run(params)
    }

    abstract suspend fun run(params: Params): Either<Failure, Type>

    class None
}