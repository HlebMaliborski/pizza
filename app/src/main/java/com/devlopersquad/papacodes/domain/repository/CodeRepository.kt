package com.devlopersquad.papacodes.domain.repository

import com.devlopersquad.papacodes.common.response.Failure
import com.devlopersquad.papacodes.common.response.Either
import com.devlopersquad.papacodes.domain.model.DomainCodeModel

interface CodeRepository {
    suspend fun storeCopiedCode(code: String): Either<Failure, String>
    suspend fun getAllCodes(): Either<Failure, DomainCodeModel>
    suspend fun getAllFilteredCodes(filter: Map<String, String>): Either<Failure, DomainCodeModel>
}