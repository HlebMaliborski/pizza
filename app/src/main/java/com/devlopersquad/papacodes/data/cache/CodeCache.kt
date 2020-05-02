package com.devlopersquad.papacodes.data.cache

import com.devlopersquad.papacodes.domain.model.DomainCodeModel

interface CodeCache {
    suspend fun get(): DomainCodeModel
    suspend fun put(codeModel: DomainCodeModel)
    suspend fun isCached(): Boolean
    suspend fun storeCopiedCode(code: String): String
}