package com.example.papacodes.domain.usecase

import com.example.core_common.result.Failure
import com.example.papacodes.common.response.Either
import com.example.papacodes.domain.model.DomainCodeModel
import com.example.papacodes.domain.repository.CodeRepository

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
