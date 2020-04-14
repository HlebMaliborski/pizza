package com.example.papacodes.data.cache

import com.example.papacodes.domain.model.DomainCodeModel

class CodeCacheImpl : CodeCache {
    private var dataCodeModel: DomainCodeModel = DomainCodeModel()

    override suspend fun get(): DomainCodeModel = dataCodeModel

    override suspend fun put(codeModel: DomainCodeModel) {
        dataCodeModel = codeModel
    }

    override suspend fun isCached(): Boolean = dataCodeModel.codes.isNotEmpty()
}