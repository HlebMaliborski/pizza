package com.devlopersquad.papacodes.domain.usecase

import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.domain.repository.CodeRepository

class GetAllCodesUseCaseImpl(private val repository: CodeRepository) : GetAllCodeUseCase() {
    override suspend fun run(params: None): Either<Failure, DomainCodeModel> {
        return when (val result = repository.getAllCodes()) {
            is Either.Error -> result
            is Either.Success -> {
                val sortedData = result.data.codes.sortedBy {
                    it.priceFrom
                }
                Either.Success(
                    DomainCodeModel(
                        sortedData,
                        result.data.maxPrice,
                        result.data.minPrice
                    )
                )
            }
        }
    }
}

abstract class GetAllCodeUseCase : BaseUseCase<DomainCodeModel, BaseUseCase.None>()