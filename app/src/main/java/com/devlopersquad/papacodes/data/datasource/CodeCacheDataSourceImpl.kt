package com.devlopersquad.papacodes.data.datasource

import com.devlopersquad.papacodes.data.cache.CodeCache
import com.devlopersquad.papacodes.domain.model.DomainCodeModel
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.CITY
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.PRICE
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.RESET
import com.devlopersquad.papacodes.presentation.viewmodel.CodeViewModel.Companion.SIZE

class CodeCacheDataSourceImpl(private val cache: CodeCache) : CodeCacheDataSource {
    override suspend fun getAllCodes(): DomainCodeModel {
        return cache.get()
    }

    override suspend fun storeCopiedCode(code: String): String {
        return cache.storeCopiedCode(code)
    }

    override suspend fun getAllCodesFilteredByCity(filter: Map<String, String>): DomainCodeModel {
        val cityFilter = filter[CITY] ?: RESET
        val sizeFilter = filter[SIZE] ?: RESET
        val priceFilter = filter[PRICE] ?: RESET

        val filteredDataByCity = if (cityFilter != RESET) cache.get().codes.filter {
            it.city.contains(cityFilter) || it.city.contains("Все города")
        } else cache.get().codes

        val filteredDataBySize = if (sizeFilter != RESET) filteredDataByCity.filter {
            it.stock.contains(sizeFilter)
        } else filteredDataByCity

        val filteredDataByPrice = if (priceFilter != RESET) {
            filteredDataBySize.filter {
                it.priceFrom <= priceFilter.toInt()
            }
        } else filteredDataBySize

        return DomainCodeModel(filteredDataByPrice, cache.get().maxPrice, cache.get().minPrice)
    }

    override suspend fun updateCodes(dataCode: DomainCodeModel) {
        cache.put(dataCode)
    }

    override suspend fun isCached(): Boolean = cache.isCached()
}

interface CodeCacheDataSource {
    suspend fun getAllCodes(): DomainCodeModel
    suspend fun storeCopiedCode(code: String): String
    suspend fun getAllCodesFilteredByCity(filter: Map<String, String>): DomainCodeModel
    suspend fun updateCodes(dataCode: DomainCodeModel)
    suspend fun isCached(): Boolean
}