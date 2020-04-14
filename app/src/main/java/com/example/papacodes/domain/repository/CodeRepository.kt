package com.example.papacodes.domain.repository

import com.example.core_common.result.Failure
import com.example.papacodes.common.response.Either
import com.example.papacodes.domain.model.DomainCodeModel

interface CodeRepository {
    suspend fun getAllCodes(): Either<Failure, DomainCodeModel>
    suspend fun getAllFilteredCodes(filter: Map<String, String>): Either<Failure, DomainCodeModel>
}