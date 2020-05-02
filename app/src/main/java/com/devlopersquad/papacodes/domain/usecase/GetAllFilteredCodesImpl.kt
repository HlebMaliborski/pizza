package com.devlopersquad.papacodes.domain.usecase

import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.domain.repository.CodeRepository

class GetAllFilteredCodesImpl(private val repository: CodeRepository) :
    GetAllFilteredCodes() {
    override suspend fun run(params: Params): Either<Failure, DomainCodeModel> {
        return when (val result = repository.getAllFilteredCodes(params.filter)) {
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

abstract class GetAllFilteredCodes : BaseUseCase<DomainCodeModel, Params>()
data class Params(val filter: Map<String, String>)
