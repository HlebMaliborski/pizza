package com.example.papacodes.domain.repository

import com.example.papacodes.common.response.Failure
import com.example.papacodes.common.response.Either
import com.example.papacodes.domain.model.DomainCodeModel

interface CodeRepository {
    suspend fun storeCopiedCode(code: String): Either<Failure, String>
    suspend fun getAllCodes(): Either<Failure, DomainCodeModel>
    suspend fun getAllFilteredCodes(filter: Map<String, String>): Either<Failure, DomainCodeModel>
}